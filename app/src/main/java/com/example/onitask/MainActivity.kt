package com.example.onitask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.R
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.onitask.ui.theme.BTNs
import com.example.onitask.ui.theme.mainBGC
import com.example.onitask.ui.theme.secondary
import com.example.onitask.ui.theme.textFieldUnfocused
import com.example.onitask.ui.theme.textFieldfocused


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //nav section
            val navStat= rememberNavController()// in page hamono navigate mikone
            NavHost(navController = navStat, startDestination = "loginSignupPage" ){
                composable(route="loginSignupPage"){
                    LoginSignupComp(navController=navStat)
                }
                composable(route="loginPage"){
                    LoginComp(navController=navStat)
                }
            }
        }
    }
}
//backBTN
@Composable

fun BackBTN(navController: NavController) {
    Box() {
    FloatingActionButton(
        onClick = { navController.popBackStack() },
        modifier = Modifier.align(Alignment.BottomEnd)
    ) {
        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Arrow Back")
    }
}
}
//login sign up page
@Composable
fun LoginSignupComp(navController: NavController){
    val intract= remember {
        MutableInteractionSource()
    }
    val isPressed by intract.collectIsPressedAsState()
    val buttonBGC= if (isPressed) secondary else BTNs

    val intractLogin= remember {
        MutableInteractionSource()
    }
    val isPressedLogin by intractLogin.collectIsPressedAsState()
    val buttonLoginBGC= if (isPressedLogin) secondary else BTNs

    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = mainBGC)
        .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement =Arrangement.Center){

        Text(text= buildAnnotatedString {
            withStyle(style= SpanStyle(fontSize = 40.sp, color = BTNs)){append("O")}
            withStyle(style= SpanStyle(fontSize = 30.sp, color = secondary)){append("niTask")}
        })
        OutlinedButton(onClick = { navController.navigate("loginPage") },
            modifier = Modifier
                .width(250.dp)
                .height(70.dp),
            border = BorderStroke(2.dp, buttonLoginBGC),
            shape = RoundedCornerShape(15.dp),
            interactionSource = intractLogin
        ) {
            Text(text = "LogIn", fontSize =30.sp,color=buttonLoginBGC)
        }
        Spacer(modifier = Modifier.height(50.dp))
        Button(onClick = { navController.navigate("signupPage") },
            interactionSource = intract,
            modifier = Modifier
                .width(250.dp)
                .height(70.dp),
            colors= ButtonDefaults.buttonColors(buttonBGC),
            shape = RoundedCornerShape(15.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp)
        ) {
            Text(text = "SignUp", fontSize =30.sp,color= Color.White, modifier = Modifier.padding(0.dp,0.dp))
        }
    }
}

// login page
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginComp(navController: NavController){
    var enteredUsername by remember {
        mutableStateOf("")
    }
    var enteredPassword by remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp)
        .background(color = mainBGC),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Text(text = "Login Page", fontSize = 30.sp, color = secondary)

        TextField(
            value = enteredUsername, onValueChange = { new -> enteredUsername = new },
            label = { Text(text = "Username") },
            modifier = Modifier
                .fillMaxWidth(0.8f),
            shape = RoundedCornerShape(10.dp),
            maxLines = 1,
            singleLine=true,
            textStyle = TextStyle(color = Color.White, fontSize = 20.sp),
            colors = TextFieldDefaults.colors(focusedContainerColor = textFieldfocused, unfocusedContainerColor = textFieldUnfocused, focusedLabelColor = Color.Black),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "usernameIcon",
                )
            },

            )

        Spacer(modifier = Modifier.height(30.dp))
        TextField(
            value = enteredPassword, onValueChange = { new -> enteredPassword = new },
            label = { Text(text = "Password") },
            modifier = Modifier
                .fillMaxWidth(0.8f),
            shape = RoundedCornerShape(10.dp),
            maxLines = 1,
            singleLine=true,
            textStyle = TextStyle(color = Color.White, fontSize = 20.sp),
            colors = TextFieldDefaults.colors(focusedContainerColor = textFieldfocused, unfocusedContainerColor = textFieldUnfocused, focusedLabelColor = Color.Black),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "usernameIcon",
                )
            },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        BackBTN(navController = navController )
    }

}
