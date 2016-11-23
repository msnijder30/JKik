# JKik
---
A complete easy to use java wrapper for the kik api.

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

To send the message it's recommended to use the shorter constructors of the classes as they are all you need to send a message.

**Note: Including a chat id is optional if you're sending to the user directly. However it should be included when sending messages to groupchats**
```
new TextMessage("hey", msg.getChat().getFrom(), msg.getChat().getChatId())
```

Sending messages to users:
```
MessageSender.sendMessages(message1, message2...);
```

Broadcasting messages to users:
```
MessageSender.broadcastMessages(message1, message2...);
```

#Sending media
You can choose to send pictures from the local system or from url. Currently you can only send videos and gifs from urls.
```
new PictureMessage(getApi(), new File("pic.png"), msg.getChat().getFrom(), msg.getChat().getChatId());


new PictureMessage("http://example.com/picture.jpg", msg.getChat().getFrom(), msg.getChat().getChatId());
```

It's also possible to send the pictures as camera, or you can choose for a custom thumbnail and name.

###Custom everything

Supported message types:
* PictureMessage
* VideoMessage
* LinkMessage
```
MessageAttribute attr = new MessageAttribute("http://s.imgur.com/images/favicon-96x96.png", "custommessage");

new PictureMessage("http://example.com/picture.jpg", msg.getChat().getFrom(), msg.getChat().getChatId(), attr);
```


###Send as camera

Supported message types:
* PictureMessage
* VideoMessage
```
new PictureMessage("http://example.com/picture.jpg", msg.getChat().getFrom(), msg.getChat().getChatId(), attr).setSendAsCamera(true);
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
new TextMessage("hey", msg.getChat().getFrom(), msg.getChat().getChatId()).setKeyboards(keyboard1, keyboard2, keyboard3...);
```

#Download
[Releases](https://github.com/msnijder30/JKik/releases/)

#More information
You can find more information here
[Official kik docs](https://dev.kik.com/#/docs/getting-started)

