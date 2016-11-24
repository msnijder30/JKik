# JKik
A complete easy to use java wrapper for the kik api.
---

###The basics
```
//Initialise the bot
KikApi api = new KikApi(username, key, 8080, new TestBot());
api.start();
		
//Get user information
User user = api.getUserInfo("username");

```
#Receiving messages

Simply create a class and let it extend KikBot. You'll be prompted to add the unimplemented methods, and you're ready to go!


###A basic bot
```
public class TestBot extends KikBot {
	
	@Override
	public void onTextReceived(TextMessage msg) {
		msg.getBody();
		msg.getId();
		msg.getMention();
		msg.getTimestamp();	
		msg.sendReply(msg1, msg2, msg3...);
	}
  
	...
}
```

#Sending messages
There are quite a few messages you can send.
* TextMessage
* PictureMessage
* VideoMessage
* LinkMessage
* ReadReceipt

Replying messages to users:

**Note: The shortest constructor of the messages can be used here.**
```
@Override
public void onTextReceived(TextMessage msg) {
	TextMessage message1 = new TextMessage("hey");
	msg.sendReply(message1, message2...);
}
```


Sending messages to users:

**Note: To send messages to users you HAVE TO use the constructor with the `to` and `chatId` parameters. The `chatId` is not required when you're not sending it in groupchats however.**
```
TextMessage message1 = new TextMessage("hey", to, chatId);
MessageSender.sendMessages(message1, message2...);
```

Broadcasting messages to users:

**Note: To broadcast messages to users you HAVE TO use the constructor with the `to` and `chatId` parameters. The `chatId` is not required when you're not sending it in groupchats however.**
```
TextMessage message1 = new TextMessage("hey", to, chatId);
MessageSender.broadcastMessages(message1, message2...);
```

#Sending media
You can choose to send pictures from the local system or from url. Currently you can only send videos and gifs from urls.
```
new PictureMessage(getApi(), new File("pic.png"));

//Use when not replying to a message but sending it with MessageSender instead
new PictureMessage(getApi(), new File("pic.png"), to, chatId);

new PictureMessage("http://example.com/picture.jpg");

//Use when not replying to a message but sending it with MessageSender instead
new PictureMessage("http://example.com/picture.jpg", to, chatId);
```

It's also possible to send the pictures as camera, or you can choose for a custom thumbnail and name.

###Custom everything

Supported message types:
* PictureMessage
* VideoMessage
* LinkMessage
```
MessageAttribute attr = new MessageAttribute("http://s.imgur.com/images/favicon-96x96.png", "custommessage");

new PictureMessage("http://example.com/picture.jpg", attr);

//Use when not replying to a message but sending it with MessageSender instead
new PictureMessage("http://example.com/picture.jpg", to, chatId, attr);
```


###Send as camera

Supported message types:
* PictureMessage
* VideoMessage
```
new PictureMessage("http://example.com/picture.jpg", attr).setSendAsCamera(true);

//Use when not replying to a message but sending it with MessageSender instead
new PictureMessage("http://example.com/picture.jpg", , to, chatId, attr).setSendAsCamera(true);
```

#Keyboards
Ofcourse, it's also possible to add keyboards to your messages. 

###Buttons
To create a keyboard you need to add buttons to it. Supported buttons are:
* TextButton
* FriendPicker

```
//The variable preselected is a list of users that are preselected when picking friends, you can remove this field if you desire.
FriendPicker picker = new FriendPicker("message", min, max, preselected);
```
and text buttons:

```
TextButton text = new TextButton("message");
```

###Creating the keyboard

```
KeyBoard kb = new KeyBoard(button1, button2, button3...);
```
It's also possible to show the keyboard to a specific user. `kb.setTo("username");`

###Adding the keyboard to a message
You can add multiple keyboards to a message. This way you can have unique keyboards for every user in a chat.
```
new TextMessage("hey").setKeyboards(keyboard1, keyboard2, keyboard3...);

//Use when not replying to a message but sending it with MessageSender instead
new TextMessage("hey", to, chatId).setKeyboards(keyboard1, keyboard2, keyboard3...);
```

#Download
[Releases](https://github.com/msnijder30/JKik/releases/)

#More information
You can find more information here:

[Official kik docs](https://dev.kik.com/#/docs/getting-started)

