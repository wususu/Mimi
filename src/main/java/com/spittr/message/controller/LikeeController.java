package com.spittr.message.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import com.spittr.authorization.annotation.*;
import com.spittr.message.core.*;
import com.spittr.message.model.*;
import com.spittr.model.ReturnModel;
import com.spittr.user.model.User;

@RestController
@RequestMapping(value="/api/message/likee")
@CrossOrigin(origins="*", maxAge=3600)
public class LikeeController {

	@Autowired
	@Qualifier("likeeServiceImpl")
	private LikeeService likeService;
	
	@Autowired
	@Qualifier("messageServiceImpl")
	private MessageService messageService;
	
	@Authorization
	@RequestMapping(value="/{mid}", method=RequestMethod.POST)
	public ReturnModel like(
			@PathVariable Long mid,
			@AutoCurrentUser User user
			){
		
		Message message = messageService.get(mid);
		
		Likee like = likeService.get(message, user);
		if (like == null) {
			like = LikeeService.newInstance(message, user);
			likeService.create(like);
		}
		
		return ReturnModel.SUCCESS(likeService.likee(like));
	}
}
