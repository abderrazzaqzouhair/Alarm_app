# Alarm_App

## Description

**Alarm_App** is an Android application that allows users to manage alarms with features such as setting alarms, playing custom sounds, and managing alarms in the background. The app is built using modern Android components such as **Fragments**, **BroadcastReceiver**, **Service**, **WorkManager**, and **MediaPlayer**. The design of the app was created using **Figma**, ensuring an intuitive and responsive user interface.

## Features

- Set, modify, and delete alarms.
- Play custom alarm sounds using **MediaPlayer**.
- Handle alarms in the background using **Services**.
- Ensure reliable alarm execution with **WorkManager**, even after device reboot.
- Interactive notifications with options to snooze or stop alarms.
- Clean and modern user interface designed in **Figma**.

## Components Used

- **Fragments**: Used to manage different UI screens for alarms and settings.
- **BroadcastReceiver**: To receive system events, such as alarm time and device reboot.
- **Service**: To manage long-running tasks like playing alarm sounds.
- **WorkManager**: For scheduling alarms to ensure they run even if the app is closed or the device is restarted.
- **MediaPlayer**: For playing the alarm sound when triggered.
- **Figma**: For designing the user interface.

## Installation

1. Clone this repository:
   ```bash
   git clone https://github.com/yourusername/Alarm_App.git
