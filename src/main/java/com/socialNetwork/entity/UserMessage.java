package com.socialNetwork.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the user_message database table.
 * 
 */
@Entity
@Table(name="user_message")
@NamedQuery(name="UserMessage.findAll", query="SELECT u FROM UserMessage u")
public class UserMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_when")
	private Date dateWhen;

	@Column(name="text_message")
	private String textMessage;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_sender_id")
	private User userSender;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_receiver_id")
	private User userReceiver;

	public UserMessage() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDateWhen() {
		return this.dateWhen;
	}

	public void setDateWhen(Date dateWhen) {
		this.dateWhen = dateWhen;
	}

	public String getTextMessage() {
		return this.textMessage;
	}

	public void setTextMessage(String textMessage) {
		this.textMessage = textMessage;
	}

	public User getUserSender() {
		return this.userSender;
	}

	public void setUserSender(User userSender) {
		this.userSender = userSender;
	}

	public User getUserReceiver() {
		return this.userReceiver;
	}

	public void setUserReceiver(User userReceiver) {
		this.userReceiver = userReceiver;
	}

}