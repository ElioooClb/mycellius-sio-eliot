import React from "react";
import { NavigationContainer } from "@react-navigation/native";
import { createNativeStackNavigator } from "@react-navigation/native-stack";
import { AuthProvider, useAuth } from "./src/auth/AuthContext";
import { Button } from "react-native";

import LoginScreen from "./src/screens/LoginScreen";
import PagesListScreen from "./src/screens/PagesListScreen";
import PageDetailScreen from "./src/screens/PageDetailScreen";
import NewPageScreen from "./src/screens/NewPageScreen";
import EditPageScreen from "./src/screens/EditPageScreen";

const Stack = createNativeStackNavigator();

//function AppNavigator() {
//  const { booting, token } = useAuth();
//
//  if (booting) return null; // option : Splash/loader
//
//  return (
//    <Stack.Navigator>
//      {!token ? (
//        <Stack.Screen name="Login" component={LoginScreen} options={{ title: "Mycellius — Login" }} />
//      ) : (
//        <>
//          <Stack.Screen name="Pages" component={PagesListScreen} options={{ title: "Pages" }} />
//          <Stack.Screen name="NewPage" component={NewPageScreen} options={{ title: "Créer une page" }} />
//          <Stack.Screen name="PageDetail" component={PageDetailScreen} options={{ title: "Détail" }} />
//          <Stack.Screen name="EditPage" component={EditPageScreen} options={{ title: "Éditer" }} />
//        </>
//      )}
//    </Stack.Navigator>
//  );
//}

function AppNavigator() {
  const { booting, token, logout } = useAuth();

  if (booting) return null;

  return (
    <Stack.Navigator
      screenOptions={{
        headerRight: () => (
          <Button title="Déconnexion" onPress={logout} />
        ),
      }}
    >
      {!token ? (
        <Stack.Screen
          name="Login"
          component={LoginScreen}
          options={{ title: "Mycellius — Login", headerRight: () => null }} // pas de logout sur login
        />
      ) : (
        <>
          <Stack.Screen
            name="Pages"
            component={PagesListScreen}
            options={{ title: "Pages" }}
          />
          <Stack.Screen
            name="PageDetail"
            component={PageDetailScreen}
            options={{
              title: "Détail",
              headerBackTitle: "Pages",          // iOS
              headerBackTitleVisible: true,      // iOS
            }}
          />
          <Stack.Screen
            name="NewPage"
            component={NewPageScreen}
            options={{ title: "Créer" }}
          />
          <Stack.Screen
            name="EditPage"
            component={EditPageScreen}
            options={{ title: "Éditer" }}
          />
        </>
      )}
    </Stack.Navigator>
  );
}

export default function App() {
  return (
    <AuthProvider>
      <NavigationContainer>
        <AppNavigator />
      </NavigationContainer>
    </AuthProvider>
  );
}