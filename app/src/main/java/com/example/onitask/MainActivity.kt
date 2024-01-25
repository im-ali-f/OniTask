package com.example.onitask

import android.app.TimePickerDialog
import android.health.connect.datatypes.units.Length
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.R
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.onitask.data.room.models.Account
import com.example.onitask.data.room.models.Task
import com.example.onitask.data.room.models.db
import com.example.onitask.data.room.models.repo
import com.example.onitask.repository.viewmodel
import com.example.onitask.ui.theme.BTNs
import com.example.onitask.ui.theme.errorBGC
import com.example.onitask.ui.theme.errorText
import com.example.onitask.ui.theme.logoutBGC
import com.example.onitask.ui.theme.mainBGC
import com.example.onitask.ui.theme.secondary
import com.example.onitask.ui.theme.secondaryBGC
import com.example.onitask.ui.theme.textFieldUnfocused
import com.example.onitask.ui.theme.textFieldfocused
import com.example.onitask.ui.theme.textFieldfocused2
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectIndexed
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //nav section
            val navStat= rememberNavController() // in page hamono navigate mikone
            val context= LocalContext.current
            val db= db.getInstance(context)
            val repo= repo(db)
            val view= viewmodel(repo)
            NavHost(navController = navStat, startDestination = "createPage" ){
                composable(route="loginSignupPage"){
                    LoginSignupComp(navController=navStat)
                }
                composable(route="loginPage"){
                    LoginComp(navController=navStat,view)
                }
                composable(route="signupPage"){
                    SignupComp(navController=navStat,view)
                }
                composable(route="toDoListPage"){
                    ToDoListComp(navStat,view)
                }
                composable(route="createPage"){
                    CreateToDoComp(navStat,view)
                }
            }
        }
    }
}


//user important informations
/*
var globalId=0
var globalUsername=""
*/
var globalId=31
var globalUsername="ali"


//backBTN
@Composable
fun BackBTN(navController: NavController) {
    Box() {
    FloatingActionButton(
        onClick = { navController.popBackStack() },
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .width(60.dp)
            .height(60.dp),
        containerColor = secondary,
    ) {
        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Arrow Back", tint = Color.White)
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
fun LoginComp(navController: NavController, viewmodel: viewmodel){

    var enteredUsername by remember {
        mutableStateOf("")
    }
    var enteredPassword by remember {
        mutableStateOf("")
    }

    val userQueryResult by viewmodel.getUser(enteredUsername,enteredPassword).collectAsState(initial = emptyList())
    var userCantFoundBoolean by remember {
        mutableStateOf(false)
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .fillMaxWidth(0.8f)
        .padding(20.dp)
        .background(color = mainBGC),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Text(text = "Login Page", fontSize = 30.sp, color = secondary)
        if(userCantFoundBoolean){
            Text(text = "Username Or Password is wrong", modifier = Modifier.fillMaxWidth(), textAlign =TextAlign.Center, color = errorText, fontSize = 20.sp)
        }
        TextField(
            value = enteredUsername, onValueChange = { new -> enteredUsername = new },
            label = { Text(text = "Username") },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            maxLines = 1,
            singleLine=true,
            isError = userCantFoundBoolean,
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
        if(userCantFoundBoolean){
            Text(text = "Username Or Password is wrong", modifier = Modifier.fillMaxWidth(), textAlign =TextAlign.Center, color = errorText, fontSize = 20.sp)
        }
        TextField(
            value = enteredPassword, onValueChange = { new -> enteredPassword = new },
            label = { Text(text = "Password") },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            maxLines = 1,
            singleLine=true,
            textStyle = TextStyle(color = Color.White, fontSize = 20.sp),
            isError = userCantFoundBoolean,
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
        Spacer(modifier = Modifier.height(20.dp))
        //submit button
        val x = LocalContext.current.applicationContext
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            BackBTN(navController = navController )
            Button(onClick = {

                if(userQueryResult.isEmpty()){
                    userCantFoundBoolean=true
                }
                else{
                    userCantFoundBoolean=false
                    globalId=userQueryResult[0].id
                    globalUsername=userQueryResult[0].username
                    //hala navigate be list todo
                    navController.navigate("toDoListPage")
                    //Toast.makeText(x,"$globalId , $globalUsername",Toast.LENGTH_LONG).show()
                }
                             }, modifier = Modifier
                .width(120.dp)
                .height(60.dp), shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BTNs),
                elevation = ButtonDefaults.buttonElevation(10.dp)) {
                Text(text = "Login !", fontSize = 20.sp)
            }
        }

    }

}

@Composable
fun SignupComp(navController: NavController ,viewmodel: viewmodel){
    var enteredUsername by remember {
        mutableStateOf("")
    }
    var enteredPassword by remember {
        mutableStateOf("")
    }
    Box(modifier = Modifier.background(color = secondaryBGC )) {
        var usernameExsitanceBoolean by remember {
            mutableStateOf(false)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth(0.8f)
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Text(text = "Signup Page", fontSize = 30.sp, color = secondary)

            if (usernameExsitanceBoolean){
                Text(text = "username exists !", textAlign = TextAlign.Center, modifier = Modifier
                    .fillMaxWidth(), color = errorText,fontSize = 20.sp)
            }

            TextField(
                value = enteredUsername, onValueChange = { new -> enteredUsername = new },
                label = { Text(text = "Username") },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                maxLines = 1,
                singleLine = true,
                textStyle = TextStyle(color = Color.White, fontSize = 20.sp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = textFieldfocused,
                    unfocusedContainerColor = textFieldUnfocused,
                    focusedLabelColor = Color.Black
                ),
                isError = usernameExsitanceBoolean,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "usernameIcon",
                    )
                },

                )

            Spacer(modifier = Modifier.height(30.dp))
            var passwordUnCorrectBoolean by remember {
                mutableStateOf(false)
            }
            fun passwordCheck(){
                if(enteredPassword.length<8){
                    passwordUnCorrectBoolean=true
                }
                else{
                    passwordUnCorrectBoolean=false
                }
            }
            if(passwordUnCorrectBoolean){
                Text(text = "password must contain 8 character", textAlign = TextAlign.Center, modifier = Modifier
                    .fillMaxWidth(), color = errorText,fontSize = 18.sp)
            }
            TextField(
                value = enteredPassword, onValueChange = { new -> enteredPassword = new },
                label = { Text(text = "Password") },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                maxLines = 1,
                singleLine = true,
                textStyle = TextStyle(color = Color.White, fontSize = 20.sp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = textFieldfocused,
                    unfocusedContainerColor = textFieldUnfocused,
                    focusedLabelColor = Color.Black
                ),
                isError = passwordUnCorrectBoolean,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "usernameIcon",
                    )
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            Spacer(modifier = Modifier.height(20.dp))
            //submit button

            val userExistanceQueryResult by  viewmodel.userExsitanceFromView(enteredUsername).collectAsState(
                    initial = emptyList()
                )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BackBTN(navController = navController)
                Button(
                    onClick = {
                        if (userExistanceQueryResult.isEmpty()){
                            usernameExsitanceBoolean=false
                            passwordCheck()
                            if(!passwordUnCorrectBoolean){
                                viewmodel.createAcc(Account(0,enteredUsername,enteredPassword))
                                navController.navigate("loginPage")
                            }
                        }
                        else{
                            passwordCheck()

                            usernameExsitanceBoolean=true
                        }


                              }, modifier = Modifier
                        .width(130.dp)
                        .height(60.dp), shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BTNs),
                    elevation = ButtonDefaults.buttonElevation(10.dp)
                ) {
                    Text(text = "Signup !", fontSize = 20.sp)
                }
            }

        }
    }
}

//toDoList
@Composable
fun ToDoListComp(navController: NavController,viewmodel: viewmodel){
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = " list will be shown here !")
    }
}

@Composable
fun NavBar(navController: NavController){
    Row (modifier = Modifier
        .fillMaxWidth()
        .background(color = secondary), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier
            .padding(start = 5.dp)
            .fillMaxWidth(0.25f)) {
            Text(
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = globalUsername,
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
        Text(text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontSize = 30.sp,
                    color = BTNs,
                    fontWeight = FontWeight.Bold
                )
            ) { append("T") }
            withStyle(style = SpanStyle(fontSize = 25.sp, color = Color.White,
                fontWeight = FontWeight.Bold)) { append("o") }
            withStyle(
                style = SpanStyle(
                    fontSize = 30.sp,
                    color = BTNs,
                    fontWeight = FontWeight.Bold
                )
            ) { append("D") }
            withStyle(style = SpanStyle(fontSize = 25.sp, color = Color.White,
                fontWeight = FontWeight.Bold)) { append("o") }
            withStyle(
                style = SpanStyle(
                    fontSize = 30.sp,
                    color = BTNs,
                    fontWeight = FontWeight.Bold
                )
            ) { append("L") }
            withStyle(style = SpanStyle(fontSize = 25.sp, color = Color.White,
                fontWeight = FontWeight.Bold)) { append("ist") }
        })
        Box(modifier =Modifier.padding(end = 5.dp) ){
            Button(onClick = { globalUsername=""
                             globalId=0
                             navController.navigate("loginSignupPage")}, colors = ButtonDefaults.buttonColors(logoutBGC), shape = RoundedCornerShape(10.dp)) {
            Text(text = "Logout")
        }}
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateToDoComp(navController: NavController,viewmodel: viewmodel){

    var canAddToDB=true
    var enteredTitle by remember {
        mutableStateOf("")
    }
    var enteredText by remember {
        mutableStateOf("")
    }
    var enteredDateStr by remember {
        mutableStateOf("")
    }
    var enteredTimeStr by remember {
        mutableStateOf("")
    }
    

    var scrollStateCol = rememberScrollState()
    if(globalUsername !=""){
        Column(modifier = Modifier
            .fillMaxSize()
            .background(secondaryBGC),
            verticalArrangement = Arrangement.SpaceBetween) {
            Column {

                NavBar(navController)
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollStateCol),  horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Create New ToDo", fontWeight = FontWeight.Bold, fontSize = 30.sp, color = BTNs)
                    TextField(value =enteredTitle ,singleLine=true, maxLines = 1, label = { Text(text = "Title", color = secondary, fontWeight = FontWeight.Bold)}, shape = RoundedCornerShape(10.dp), modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = textFieldfocused2,
                            unfocusedContainerColor = textFieldUnfocused,
                            focusedLabelColor = Color.Black),
                        textStyle = TextStyle(fontSize = 20.sp, color = Color.Black), onValueChange ={ new:String -> enteredTitle=new} )
                    Spacer(modifier = Modifier.height(20.dp))
                    TextField(value =enteredText ,singleLine=true, maxLines = 1, label = { Text(text = "Text", color = secondary, fontWeight = FontWeight.Bold)}, shape = RoundedCornerShape(10.dp), modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),

                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = textFieldfocused2,
                            unfocusedContainerColor = textFieldUnfocused,
                            focusedLabelColor = Color.Black),
                        textStyle = TextStyle(fontSize = 20.sp, color = Color.Black), onValueChange ={ new:String -> enteredText=new} )
                    Spacer(modifier = Modifier.height(20.dp))

                    //date picker medium.com estefade kardam
                    val openDialog = remember { mutableStateOf(true) }
                    val dateState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)

                    if (openDialog.value) {
                        DatePickerDialog(
                            colors = DatePickerDefaults.colors(
                                containerColor = secondaryBGC,
                                titleContentColor = secondary,
                                headlineContentColor = secondary,
                                weekdayContentColor = textFieldUnfocused
                            ),
                            onDismissRequest = { openDialog.value = false },
                            confirmButton = { TextButton(
                                onClick = {
                                    openDialog.value = false
                                }
                            ) {
                                Text("OK")
                            }
                            },
                            dismissButton = {
                                TextButton(
                                    onClick = {
                                        openDialog.value = false
                                    }
                                ) {
                                    Text("CANCEL")
                                }
                            }
                        ) {
                            DatePicker(
                                state = dateState,
                                title = {Text(text = "select ToDo Date", fontSize = 15.sp, modifier = Modifier.padding(25.dp), color = secondary)},
                                headline ={Text(text = "Entered date:", fontSize = 35.sp, modifier = Modifier.padding(start = 20.dp, bottom = 20.dp), color = secondary)},
                                showModeToggle = false,
                            )
                        }
                        var selectedDate=dateState.selectedDateMillis?.let { Instant.ofEpochMilli(it) }
                        val selectedDateFormater=DateTimeFormatter.ofPattern("dd-MM-yyyy")
                        if (selectedDate != null) {
                            enteredDateStr=selectedDateFormater.format(selectedDate.atZone(ZoneId.systemDefault()))
                        }
                        else{
                            enteredDateStr="select Date"
                        }
                    }


                    //show selected Date
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Date: $enteredDateStr", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Bold)
                        //call date picker again
                        Button(onClick = { openDialog.value=true }
                            , modifier = Modifier
                                .width(130.dp)
                                .height(60.dp), shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = BTNs),
                            elevation = ButtonDefaults.buttonElevation(10.dp)) {
                            Text(text = "Select !", fontSize = 20.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                    // time Picker


                    val timeState = rememberTimePickerState()
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                        TimeInput(
                            state = timeState,
                            colors = TimePickerDefaults.colors(
                                timeSelectorSelectedContainerColor = Color.White,
                                timeSelectorUnselectedContainerColor=Color.White,
                                periodSelectorSelectedContainerColor= BTNs
                            )
                        )
                    }
                    enteredTimeStr="${timeState.hour}:${timeState.minute}"

                    Text(text = "Time: $enteredTimeStr", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Bold)



                }
            }

            //inja btn done baraye nav va db
            Box(modifier = Modifier
                .fillMaxWidth()
                .background(BTNs)
                .height(80.dp),
                contentAlignment = Alignment.Center
            ){
                Button(onClick = {

                                 //add new task to db

                                 if(canAddToDB && enteredDateStr != "select Date") {
                                     canAddToDB = false
                                     viewmodel.createTask(
                                         Task(
                                             id = 0,
                                             title = enteredTitle,
                                             text = enteredText,
                                             date = enteredDateStr,
                                             time = enteredTimeStr,
                                             completed = false,
                                             userIdFk = globalId
                                         )
                                     )
                                     //navigate to toDoList
                                     navController.navigate("toDoListPage")
                                 }


                }
                    , modifier = Modifier
                        .width(260.dp)
                        .height(60.dp), shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = secondary),
                    elevation = ButtonDefaults.buttonElevation(10.dp)) {
                    Text(text = "Done !", fontSize = 20.sp)
                }
                }

            }

    }
    else{
        navController.navigate("loginSignupPage")
    }

}