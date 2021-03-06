[![Maven Central](https://img.shields.io/maven-central/v/com.github.msnijder30/jkik.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.msnijder30%22%20AND%20a:%22jkik%22)
![Hex.pm](https://img.shields.io/hexpm/l/plug.svg)


# JKik
A complete easy to use java wrapper for the kik api.
---

# The basics
```java
public static void main(String[] args) throws Exception { 
	//Initialise the bot
	KikApi api = new KikApi(username, key, 8080, new TestBot());
	api.start();
}
```

## User information
```java
	//Get user information
	User user = api.getUserInfo("username");
```

## Kik codes
```java
	String url = api.generateKikCodeUrl("input string here", KikColor.ORANGE_CREAMSICLE);
```

## Optional
### Settings
The `KikSettings` dataclass allows you to set the settings for:
* manually sending read receipts
* receiving read receipts
* receive delivery receipts
* receive is typing receipts

Simply instantiate the KikApi like this:
```java
KikSettings settings = new KikSettings(true, true, true, true);
KikApi api = new KikApi(username, key, 8080, new TestBot(), settings);
```

### Static keyboard

For more about keyboards go to: 
[Keyboards](#keyboards)

```java
KeyBoard kb = new KeyBoard(button1, button2, button3...);
KikApi api = new KikApi(username, key, 8080, new TestBot(), kb);
```

### Both
```java
KikSettings settings = new KikSettings(true, true, true, true);
KeyBoard kb = new KeyBoard(button1, button2, button3...);

KikApi api = new KikApi(username, key, 8080, new TestBot(), settings, kb);

```

# Receiving messages

Simply create a class and let it extend `KikBot`. You'll be prompted to add the unimplemented methods, and you're ready to go!


### A basic bot
```java
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


# Sending messages
There are quite a few messages you can send.
* `TextMessage`
* `PictureMessage`
* `VideoMessage`
* `LinkMessage`
* `ReadReceipt`

Replying messages to users:

**Note: The shortest constructor of the messages can be used here.**
```java
@Override
public void onTextReceived(TextMessage msg) {
	TextMessage message1 = new TextMessage("hey");
	msg.sendReply(message1, message2...);
}
```


Sending messages to users:

**Note: To send messages to groupchats you HAVE TO use the constructor with the `to` and `chatId` parameters. The `chatId` is not required when you're not sending it in groupchats however.**
```java
TextMessage message1 = new TextMessage("hey", to, chatId);
MessageSender.sendMessages(message1, message2...);
```

Broadcasting messages to users:

**Note: To broadcast messages to groupchats you HAVE TO use the constructor with the `to` and `chatId` parameters. The `chatId` is not required when you're not sending it in groupchats however.**
```java
TextMessage message1 = new TextMessage("hey", to, chatId);
MessageSender.broadcastMessages(message1, message2...);
```

### Send manual read receipts
Make sure you've enabled manually sending read receipts in the `KikConfig` and instantiated the `KikApi` with it.

The id's in the read receipt constructor are the id's of the messages you want to read.
```java
ReadReceipt read = new ReadReceipt(id1, id2, id3, id4);
MessageSender.sendMessages(read);

```


# Sending media
### From link:
```java

new PictureMessage("http://example.com/picture.jpg");

//Use when not replying to a message but sending it with MessageSender instead
new PictureMessage("http://example.com/picture.jpg", to, chatId);


new VideoMessage("http://example.com/video.mp4");

//Use when not replying to a message but sending it with MessageSender instead
new VideoMessage("http://example.com/video.mp4", to, chatId);
```

### From local file:
When sending from local the api's fileserver will serve the file to kik.
You can remove local files using `api.getFiles().remove(uuid)`
```java
new PictureMessage(getApi(), new File("pic.png"));

//Use when not replying to a message but sending it with MessageSender instead
new PictureMessage(getApi(), new File("pic.png"), to, chatId);

new VideoMessage(getApi(), new File("video.mp4"));

//Use when not replying to a message but sending it with MessageSender instead
new VideoMessage(getApi(), new File("video.mp4"), to, chatId);

new VideoMessage(getApi(), new File("video.gif"));

//Use when not replying to a message but sending it with MessageSender instead
new VideoMessage(getApi(), new File("video.gif"), to, chatId);
```


### Custom
It's also possible to send the pictures as camera, or you can choose a custom thumbnail and name using the `MessageAttribute` class.

Supported message types:
* `PictureMessage`
* `VideoMessage`
* `LinkMessage`
```java
MessageAttribute attr = new MessageAttribute("http://s.imgur.com/images/favicon-96x96.png", "custommessage");

new PictureMessage("http://example.com/picture.jpg", attr);

//Use when not replying to a message but sending it with MessageSender instead
new PictureMessage("http://example.com/picture.jpg", to, chatId, attr);


new VideoMessage("http://example.com/video.mp4", attr);

//Use when not replying to a message but sending it with MessageSender instead
new VideoMessage("http://example.com/video.mp4", to, chatId, attr);


new LinkMessage("http://example.com/", attr);

//Use when not replying to a message but sending it with MessageSender instead
new LinkMessage("http://example.com/", to, chatId, attr);
```


### Send as camera

Supported message types:
* `PictureMessage`
* `VideoMessage`
```java
new PictureMessage("http://example.com/picture.jpg", attr).setSendAsCamera(true);

//Use when not replying to a message but sending it with MessageSender instead
new PictureMessage("http://example.com/picture.jpg", , to, chatId, attr).setSendAsCamera(true);
```


# Keyboards
Ofcourse, it's also possible to add keyboards to your messages. 

### Buttons
To create a keyboard you need to add buttons to it. Supported buttons are:
* `TextButton`
* `FriendPicker`

```java
//The variable preselected is a list of users that are preselected when picking friends, you can remove this field if you desire.
FriendPicker picker = new FriendPicker("message", min, max, preselected);
```
and text buttons:

```java
TextButton text = new TextButton("message");
```

### Creating the keyboard

```java
KeyBoard kb = new KeyBoard(button1, button2, button3...);
```
It's also possible to show the keyboard to a specific user. `kb.setTo("username");`

### Adding the keyboard to a message
You can add multiple keyboards to a message. This way you can have unique keyboards for every user in a chat.
```java
new TextMessage("hey").setKeyboards(keyboard1, keyboard2, keyboard3...);

//Use when not replying to a message but sending it with MessageSender instead
new TextMessage("hey", to, chatId).setKeyboards(keyboard1, keyboard2, keyboard3...);
```


# Download
[Releases](https://github.com/msnijder30/JKik/releases/)


# More information
You can find more information here:


[Official kik docs](https://dev.kik.com/#/docs/getting-started)

This project uses the following libraries:
* [Google Gson](https://github.com/google/gson)
* [Lombok](https://projectlombok.org/)


