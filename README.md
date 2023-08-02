# Romi and Photon Vision Demo

The code in this repo makes a Romi follow an april tag target as it
moves around. When a target is not found you can drive the Romi around
looking for a target.  You can toggle the follow target action on/off
by pressing the Y button on a controller.

# What do you need to run this code?

Read through all the steps in this file and click on links and read
all the instructions.  Make sure you understand what you have to do
and have all the physical items you need ahead of time.

You will need to have the following things:

- Romi HW and the following items to make it work:- Romi HW and the following items:
  - 6 AA batteries (Romi eats up batteries, consider getting rechargeables if you plan on doing alot of Romi work)
  - SD card (8GB min)
  - usb to sd dongle(something that lets you plug sd card into laptop if your laptop has no sd card port)
  - raspberry PI
- xbox/playstation style controller
- USB camera (microsoft life-cam is most common camera we have so get one of those if you can)
- april tag printed out
  - direct link https://firstfrc.blob.core.windows.net/frc2023/FieldAssets/TeamVersions/AprilTags-UserGuideandImages.pdf
  - if direct link is broken see if link/ref to april tags is here: https://www.firstinspires.org/robotics/frc/playing-field
- laptop for development
- flat space to drive the Romi. This isn't an offroad robot.  You need flat surface or thin carpet.

If you have not installed/setup Romi or laptop you will also need
decent internet to download images, packages, etc... Give yourself
extra time to do all the downloading/setup/install.  Take your time
and read instructions carefully.


## Romi Setup

See these instructions for setting up a Romi:
https://docs.wpilib.org/en/stable/docs/romi-robot/index.html#getting-started-with-romi

Add Photon Vision to the Romi by following these instructions:
https://docs.photonvision.org/en/latest/docs/getting-started/installation/sw_install/romi.html

This demo used a Microsoft LifeCam.  It plugs in to one of the USB
ports on the RaspberryPi on the Romi.  You can wedge the camera
between the Pi and the Romi HW board.  USB cable was just kind of
wrapped/wedged so it would not drag around the ground.  Nothing
fancy/special was done to attach the camera physically to the Romi.

## Laptop Setup

If you have not already done so install WPILib, instructions are here:
https://docs.wpilib.org/en/stable/docs/zero-to-robot/step-2/wpilib-setup.html

Photon Vision gets installed when you load the repo/project in vscode.  

You DO NOT need to install Photon Vision on your laptop to make this
demo work.  You DO NOT need to follow instructions here:
https://docs.photonvision.org/en/latest/docs/getting-started/installation/sw_install/index.html#desktop-environments
If you want to play with Photon Vision by plugging USB camera into
your laptop you can install and run Photon Vision on your laptop.
This does not require a Romi and has nothing to do with running this
repo/code on a Romi.

# Running the code

Clone this repo to your laptop.  Open it up in vscode.  Then follow
instructions for running a Romi program:
https://docs.wpilib.org/en/stable/docs/romi-robot/programming-romi.html#running-a-romi-program

Once you have this repo running on your Romi you should be able to
just drive the Romi around using a controller.  That's a good first
step.

Next you can verify that the USB camera and Photon Vision is working.
At the bottom of
https://docs.photonvision.org/en/latest/docs/getting-started/installation/sw_install/romi.html
it mentions how to connect to the Photon Vision UI running on the
Romi/PI: 

```
http://10.0.0.2:5800/
```

If that page loads you know Photon Vision is working.  Keep that page
open in a tab while you put an april tag in front of the Romi.

If you are using a Microsoft Lifecam great.  If not you may need to
edit this line
https://github.com/BHSRobotix/RomiVision2023/blob/119367c918a0a2a727d06e18136cce3eac267aa4/src/main/java/frc/robot/subsystems/Vision.java#L27
to change the name of the camera to match what you are using.  You can
get the exact string to use from the Photon Vision UI.

You may also need to configure Photon Vision to look for april tags.
By default it is not setup to look for them.  See:
https://docs.photonvision.org/en/latest/docs/getting-started/pipeline-tuning/about-pipelines.html
You only need to do this once when you first run the demo.

Depending where you are, how much/little light there is, you may need
to adjust settings on the pipeline.  See
https://docs.photonvision.org/en/latest/docs/getting-started/pipeline-tuning/apriltag-tuning.html

# Troubleshooting

Add things here as others try/find things out

## Romi beeps

Some Romis will beep when batteries get low.  If you have been using
your Romi for a few hours and you hear it start beeping change the
batteries.  If you see Romi start behaving strangely after it was
working just fine, check the batteries.  Romi does not always beep
when batteries are low.
