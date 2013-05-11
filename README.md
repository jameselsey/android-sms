android-sms
===========

![Screenshot](https://raw.github.com/jameselsey/android-sms/master/github_assets/screenshot.png)

With a 3 hour driving commute, its quite frustrating when you get an SMS message and are unable to read it (as you're a good, legal driver of course).

This simple app attempts to solve that problem.

The app consists of a single screen, with a button to enable or disable the speaking of SMS messages

Once enabled, any incoming SMS messages will be spoken out via text-to-speech, so you would hear something like the following :

`SMS received from James, message is: What time will you be home at?`

To avoid any embarassments, be sure to disable the app before attending meetings ;)


## How to build / run the app
1. `git clone https://github.com/jameselsey/android-sms.git`
2. Build and install to attached devices using `mvn clean install android:deploy`
3. Telnet to the device (only works for emulators) using `telnet localhost 5554` (or whatever port your emulator is running on, `adb devices` will help you there)
4. Send an sms to the device using `sms send 07843254333 Hello this is my message`


## Future enhancements / contributions
* If the device is on silent or vibrate mode, don't attempt to speak anything, ever
* If the SMS comes from a known contact, speak the contacts name instead of phone number
* Allow the user to specify their own announcement string, using tokenisation such as SENDER and MESSAGE
* Similar process for emails?
* Test coverage (thats more of a learning thing for me)

Like it? Hate it? Let me know on my twitter [@jameselsey1986](https://twitter.com/jameselsey1986)
