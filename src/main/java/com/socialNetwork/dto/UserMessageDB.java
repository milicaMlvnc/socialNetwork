package com.socialNetwork.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.socialNetwork.entity.UserMessage;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class UserMessageDB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Date dateWhen;
	private String textMessage;
	
	private UserDB fkUserSender;
	private UserDB fkUserReceiver;
	
	public UserMessageDB(UserMessage ent) {
		this(ent, false);
	}
	
	public UserMessageDB(UserMessage ent, Boolean includeFks) {
		this.id = ent.getId();
		this.dateWhen = ent.getDateWhen();
		this.textMessage = ent.getTextMessage();

		if (includeFks) {
			this.fkUserSender = new UserDB(ent.getUserSender());
			this.fkUserReceiver = new UserDB(ent.getUserReceiver());
		}
	}
	
}
