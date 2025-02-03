# expo-dynamic-app-icon

Expo module and plugin to change the app icon progammatically in Expo. Works on iOS, Android and Web (no-op) and supports Expo 52.

For iOS, this module relies on [UIApplication.shared.setAlternateIconName](https://developer.apple.com/documentation/xcode/configuring-your-app-icon)

For Android, this is no native support for changing app icon progammatically, so we use [activity-alias](https://developer.android.com/guide/topics/manifest/activity-alias-element) and activity lifecycles to change app icons safely.

For Web, it calls back to a [no-op](https://en.wikipedia.org/wiki/NOP_(code))

# Install

```npx expo install @variant-systems/expo-dynamic-app-icon```

# Plugin

`@variant-systems/expo-dynamic-app-icon` comes with a plugin to modily `Info.plist` and `Manifest` files.

So start my adding the plugin to `app.json` file

```
"plugins": [
  [
    "@variant-systems/expo-dynamic-app-icon",
    {
      "rabbit": {
        "image": "./assets/rabbit.png",
        "prerendered": true
      },
      "goose": {
        "image": "./assets/goose.png"
      }
    }
  ]
]
```

Since the plugin handles generating images for all densities automatically, you only need to pass images in your base resolution, and the plugin will take of the rest.

```
interface Config {
  [iconName: string]: {
    "image": string, // icon path
    "prerender"?: boolean // for ios UIPrerenderedIcon option
  }
} 
```

```
"plugins": [
  [
    "@variant-systems/expo-dynamic-app-icon", config
  ]
]
```

# Usage

```
import ExpoDynamicAppIcon from "expo-dynamic-app-icon";
import { Text, View, Image, Pressable, SafeAreaView } from "react-native";

export default function App() {
  return (
    <SafeAreaView style={styles.container}>
      <View style={styles.container}>
        <Text style={styles.header}>
          Variant Systems | Dynamic App Icon Example
        </Text>

        <View
          style={{
            justifyContent: "center",
            alignItems: "center",
            gap: 24,
          }}
        >
          <View
            style={{
              justifyContent: "center",
              alignItems: "center",
              gap: 12,
            }}
          >
            <Image
              style={styles.logo}
              source={require("./assets/rabbit.png")}
            />
            <Pressable
              style={styles.button}
              onPress={() => {
                ExpoDynamicAppIcon.setAppIcon("rabbit");
              }}
            >
              <Text style={styles.text}>Set Rabbit Icon</Text>
            </Pressable>
          </View>
          <View
            style={{
              justifyContent: "center",
              alignItems: "center",
              gap: 12,
            }}
          >
            <Image style={styles.logo} source={require("./assets/goose.png")} />
            <Pressable
              style={styles.button}
              onPress={() => {
                ExpoDynamicAppIcon.setAppIcon("goose");
              }}
            >
              <Text style={styles.text}>Set Goose Icon</Text>
            </Pressable>
          </View>
        </View>
      </View>
    </SafeAreaView>
  );
}

const styles = {
  container: {
    flex: 1,
    backgroundColor: "#eee",
  },

  header: {
    fontSize: 30,
    margin: 20,
  },

  logo: {
    width: 120,
    height: 120,
  },

  button: {
    padding: 12,
    borderRadius: 4,
    backgroundColor: "rgb(33, 150, 243)",
  },

  text: {
    fontSize: 24,
    color: "#eee",
  },
};
```

# Example

Clone this repo, `cd example`, `npx expo prebuild --clean` and run on your favorite platform.

`npx expo run:ios`
`npx expo run:android`
