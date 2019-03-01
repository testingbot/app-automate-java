App Automate Java
====================

Currently this serves as an example on how to upload an .apk or .ipa file to [TestingBot Storage](https://testingbot.com/support/api#upload).

Getting Started
----------------

Simply set your TestingBot key and secret as environment variable:

```bash
export TESTINGBOT_KEY = ...
export TESTINGBOT_SECRET = ..
```

Next, build the project:

```bash
mvn compile
```

Now you can upload a file to TestingBot Storage:

```bash
java -jar uploadApp-1.0.3-jar-with-dependencies.jar "/Users/../sample.apk"
```

This command should return a successful response:


```
{"app_url":"tb://8130affdac207ff0baa259e8"}
```

This `tb://` url can now be used in your Automated App Tests on TestingBot.
