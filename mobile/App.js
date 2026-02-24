import React from "react";
import { NavigationContainer } from "@react-navigation/native";
import { createNativeStackNavigator } from "@react-navigation/native-stack";
import { AuthProvider, useAuth } from "./src/auth/AuthContext";
import LoginScreen from "./src/screens/LoginScreen";
import PagesListScreen from "./src/screens/PagesListScreen";
import PageDetailScreen from "./src/screens/PageDetailScreen";
const Stack = createNativeStackNavigator();
function AppNavigator() {
 const { booting, token } = useAuth();
 if (booting) return null; // option : Splash/loader
 return (
 <Stack.Navigator>
 {!token ? (
 <Stack.Screen name="Login" component={LoginScreen} options={{ title: "Mycellius — Login" }} />
 ) : (
 <>
 <Stack.Screen name="Pages" component={PagesListScreen} options={{ title: "Pages" }} />
 <Stack.Screen name="PageDetail" component={PageDetailScreen} options={{ title: "Détail" }} />
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