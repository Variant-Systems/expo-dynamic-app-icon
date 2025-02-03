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
