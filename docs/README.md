<img src="https://static.mparticle.com/sdk/mp_logo_black.svg" width="280">

## Inspector Gadget

Hello, welcome to the Inspector Gadget alpha!

The Inspector is a UI widget which attaches to the [mParticle Android SDK](https://git.corp.mparticle.com/mParticle/mparticle-android-sdk). It's designed to help you debug your mParticle implementation and API usage, as well as the inner workings of the mParticle Android SDK.


#### Requirements:

* MinSdkVersion 16+

* CompileSdkVersion 28

* Support Libraries >= 26.1.0


### Adding the Inspector

The functionality of the Widget depends on a new listener interface for the mParticle Android SDK, which is not present in our public artifact. Because of this, in order to use the Inspector, you will also need to build the Android SDK from the source.

1) Clone mParticle Android SDK

   ```sh
   git clone git@github.com:mParticle/mparticle-android-sdk.git
   cd mparticle-android-sdk
   git checkout inspector
   ```

2) Build and deploy the SDK to a local maven repo

    ```sh
    ./gradlew uploadArchives
    ```

3) Clone the Inspector

    ```sh
    git clone git@github.com:mParticle/mparticle-android-inspector.git`
    cd mparticle-android-inspector
    ```

4) Build and deploy the Inspector to a local maven repo

    ```sh
    ./gradlew uploadArchives
    ```

5) Add the Inspector dependency to your app.

   - In your app-level `build.gradle` file, add the following dependency:

      ```groovy
      implementation 'com.mparticle:android-inspector:0.1-SNAPSHOT
      ```

    > **Note**: You should not ship the Inspector to a production app. For this reason you should only add the dependency to a debug or non-Google Play variant.

   - You'll also need to add the `mavenLocal()` repository:

      ```groovy
      repositories {
          mavenLocal()
      }
      ```

6) Force usage of the alpha mParticle Android SDK

    You may already have a dependency for `com.mparticle:android-core` in you project and any partner kits you may have already include a transitive dependency on it as well. You must force any package that depends on `com.mparticle:android-core` to depend on the alpha version of the SDK. Make sure all of your kits are using the latest version, and add the additional snippet

    One easy way to force your transitive dependencies to point to the alpha SDK artifact is to add the following snippet in you app level `build.gradle`:

   ```groovy
   configurations.all {
       resolutionStrategy {
           force 'com.mparticle:android-core:5.7.8-SNAPSHOT'
       }
   }
   ```

## Initializing the Inspector

By default, the Inspector will auto-start, as long as it has been added as a dependency of your app. There is **no initialization code required**.

## Viewing the Inspector

When the application starts, the Inspector will not be visible. **By default, the Inspector will become visible when a shake gesture is detected**. Simply shake the device 3-4 times and the Inspector will become visible.

You can also make the Inspector visible programatically. This is very useful if you are using an Emulator, and are unable to perform a shake gesture.

#### Kotlin

```kt
WidgetApi.getInstance()?.visible = true
```

#### Java

```java
WidgetApi.Companion.getInstance().setVisible(true);
```

### Exploring the Inspector's Views

There are three main views in the Inspector. You can navigate between the three by swiping vertically. All three will not necessarily be present at any given time.

Most events that populate these views are expandable, which is done with a simple click. When an event is expanded, you might notice that some have fields with an orange background. **This indicates that the event is "followable" in the PathFinder view**. If you click the orange area, you will be taken to the PathFinder View for that event. Events that are followable currently consist of certain API and network events.

#### Feed View

This is a real-time chronological list of SDK events. Events populate from the bottom in chronological order

#### All Events View

This list is a categorized collection of all the events that have been collected since the SDK was started. Most categories are arranged chronologically and some such as "state" are pinned.

#### PathFinder View

The PathFinder view is used to show the causal relationship between related events. This is where you will see the events that occurred because of another event, or the events that went into an event. This view exposed the inner working of the mParticle Android SDK for dissection.

For example, if your code calls `MParticle.getInstance.logEvent()` you can select the resulting "API call" event and you should see the resulting "message" that was created as a result of the API call. Then an "upload" message that was created as a result of the "message", and finally the "events" network request that was completed as a result of the upload message.

**Click** the orange area of a "followable" event to be taken to the PathFinder view

<img src="./Inspector_Clickable.png" width=200 height=200>

### Interacting with the Inspector

The Inspector can be pinned to either the top or bottom of the screen.

#### Resize

**Press and Drag** the teal button on the right hand, middle limit of the Inspector view to resize the window

<img src="./Inspector_Resize.png" width=200 height=100>

#### Pin to top/bottom, dismiss

**Long Press and Drag** the area to the left or right of the MParticle logo.

<img src="./Inspector_Drag.png" width=200 height=100>

* Drag to the top or bottom of the screen in order to pin to the top or bottom

* Drag off the screen in order to hide Inspector. To show the Inspector again, use a shake gesture

#### Dismiss temporarily

**Double Tap** the area to the left or right of the MParticle logo in order to make the Inspector temporarily disappear for 5 seconds

<img src="./Inspector_Drag.png" width=200 height=100>

#### Export Events

**Click** the logo in order to get an Export prompt

<img src="./Inspector_Export.png" width=200 height=100>
