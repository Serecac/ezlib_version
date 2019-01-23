<img src="https://github.com/Serecac/ezlib_version/blob/master/ezlib.png" alt="Ezlib" width="128" height="128">

Ezlib Version
=======

This library allows to write logs cleaner and with better visibility in the logcat.

How to use
-------
Put the .aar file (Ezlib_Version_x.y.z.aar) in the folder "Libs" inside the module.

In your project build.gradle. Add the following lines
```
flatDir {
	dirs 'libs'
}
```
```
dependencies {
	implementation(name: 'Ezlib_Version_x.y.z', ext: 'aar')
}
```

------------------------------------------------------

There are two ways to access EzlibVersionManager:
- Accessing directly using EzlibVersionInstance
- Use dagger to inject the dependencies using EzlibVersionModule


**EzlibVersionInstance**

The instance must be initialized (it is not mandatory). It is recommended to do it at the beginning in the Application class.
```
EzlibVersionInstance.init(<EzlibVersionConfiguration>);
```

**EzlibVersionModule**

The library provides a module that can be injected directly by dagger into your project.
```
@Provides
@Singleton
EzlibVersionManager provideEzlibVersionManager(EzlibVersionConfiguration configuration) {
	return new EzlibVersionManager(configuration);
}
```

------------------------------------------------------

**EzlibVersionConfiguration**

It is mandatory to initialize the manager with the configuration that will personalize the warnings, which will be shown to the user in case of needing an update.
The values that must be configured are:

* preferencesName => Name used for storage in SharedPreferences
* context => Context of app
* attempToAskForUpdate = Attempt number before showing the next notice.
* titleUpdate => Text to show in the title
* textUpdate => Text to show in the body
* iconSmallUpdate = Small icon for notification
* iconLargeUpdate = Large icon for notification
* parentHeight = Parent height
* parentWidth = Parent width
* hasTypeface = Indicates if you want to use a font in texts
* typeface = Font to use in texts
* dialogMainColor = Main color;
* dialogSecondColor = Second color;
* dialogTextColor = Text color;

How to use
-------

There are 3 ways to check the version:
*checkVersion => If the indicated version is higher than the current version, the user is notified according to the defined type.
*checkWithOnlineVersion => If the version of the application currently in production (in Google Play) is higher than the current version, the user is notified according to the defined type.
*checkWithVersionList => If the current version is found in the version list, the user is notified according to the defined type.


Contribution
=======
You are always welcome to contribute and help us mantain the library. 

<img src="https://github.com/Serecac/ezlib_version/blob/master/ezlib.png" alt="Ezlib" width="128" height="128">