package com.spittr.websocket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import com.spittr.user.core.UserService;
import com.spittr.user.model.User;
import com.spittr.websocket.model.SendMsg;

import com.spittr.websocket.core.ChatMsgService;
import com.spittr.websocket.core.NtcService;
import com.spittr.websocket.core.SocketAuthorization;
import com.spittr.websocket.exception.ChatMsgReciverErrorException;
import com.spittr.websocket.exception.ParamersErrorException;
import com.spittr.websocket.exception.ParamersRequiredException;
import com.spittr.websocket.model.*;

import static com.spittr.websocket.config.WebSocketConstant.*;


@Controller
public class UserChatController {
	
	@Autowired
	protected SimpMessagingTemplate msgTplt;
		
	@Autowired
	@Qualifier("userServiceImpl")
	protected UserService userService;
	
	@Autowired
	@Qualifier("chatMsgServiceImpl")
	protected ChatMsgService chatMsgService;
	
	@Autowired
	@Qualifier("ntcServiceImpl")
	protected NtcService ntcService;
	
	@MessageMapping("/chat/init")
	public void connectionInit(Message<Object>message){
		User user = SocketAuthorization.getUser(message);
		if (user != null) {
			List<ChatMsg> chatMsgsNotRecived = chatMsgService.getNotRecivedChatMsg(user.getUid());
			for (ChatMsg chatMsg : chatMsgsNotRecived) {
				msgTplt.convertAndSendToUser(user.getUname(), "/recive", chatMsg);
				msgTplt.convertAndSendToUser(String.valueOf(user.getUid()), "/recive", chatMsg);
			}
			
			ntcInit(user);
		}
	}
	
	private void ntcInit(User user){
		List<NtcMsg> ntcMsgs = ntcService.getNtcNotRcv(user.getUid());
		System.err.println("SIZEEEEEEEEEEEEE:"+ntcMsgs.size());
		for (NtcMsg ntcMsg : ntcMsgs) {
			System.err.println("init ntccccccccccc");
			msgTplt.convertAndSendToUser(user.getUname(), "/notice", ntcMsg);
			msgTplt.convertAndSendToUser(String.valueOf(user.getUid()), "/notice", ntcMsg);
		}
	}
	
	/**
	 * 聊天信息发送接口
	 * 接收用户消息后保存到数据库, 同时发送给接收目标用户, 回复发送用户确认发送
	 * @param msg
	 * @throws Exception
	 */
	@MessageMapping("/chat/send")
	@SendToUser(value="/queue/send/status")
	public StatusMsg sendToUser(Message<Object>message, SendMsg msg) throws Exception{
		if (msg.getMsgID() == null) 
			return null;
		if (msg.getReciverId() == null) 
			throw new ParamersRequiredException(RECIVERID);
		if (msg.getMessage() == null) 
			throw new ParamersRequiredException(MESSAGE);
		
		User sender = null;
		
		sender = SocketAuthorization.getUser(message);

		Long reciverId = msg.getReciverId();
		
		User reciver = userService.get(reciverId);
				
		if (reciver == null || sender.equals(reciver)){
			throw new ChatMsgReciverErrorException();
		}
		
		ChatMsg chatMsg =  ChatMsgService.newInstance(sender, reciver, msg);
		chatMsgService.sended(chatMsg);
		
		msgTplt.convertAndSendToUser(reciver.getUname(), "/recive", chatMsg);
		msgTplt.convertAndSendToUser(String.valueOf( reciver.getUid() ), "/recive", chatMsg);

		return StatusMsg.OK(chatMsg.getChatID());
	}
	
	/**
	 * 聊天消息接收方确认接收接口
	 * 
	 * @param msg
	 */
	@MessageMapping(value="/chat/recive")
	@SendToUser(value="/queue/recive/status")
	public StatusMsg recivedConfirm(ReciveMsg msg){
		String chatID = msg.getMsgID();
		
		if (chatID == null) 
			return null;
		
		ChatMsg chatMsg = chatMsgService.get(chatID);
		if (chatMsg == null) {
			throw new ParamersErrorException(CHATID);
		}
		chatMsgService.recived(chatMsg);
		return StatusMsg.OK(chatID);
	}
		
	@MessageExceptionHandler
	@SendToUser(value="/queue/status")
	public Object handleExceptions(RuntimeException e, Message<Object> msg, SendMsg sendMsg) {  
		e.printStackTrace();
		ErrorMsg errorMsg = ErrorMsg.newInstance(e, msg);
		return StatusMsg.ERROR(sendMsg.getMsgID(), errorMsg);
	}  
}
