package com.codelabs.basicstatecodelab

import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codelabs.basicstatecodelab.ui.theme.BasicStateCodelabTheme
import java.util.logging.Logger

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            BasicStateCodelabTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colors.background
//                ) {
//
//                }
//            }
//            WellnessScreen()
//            WaterCounter()
//            StatefulCounter()
//            WellnessTasksList()
            WellnessScreen()
        }
    }
}

//@Composable
//fun WaterCounter(modifier: Modifier = Modifier) {
//    Column(modifier = modifier.padding(16.dp)) {
////        var count = 0
////        val count : MutableState<Int> =  mutableStateOf(0)
//
//        var count by remember { mutableStateOf(0) }
//
//        if(count > 0) {
//            Text(
//                text = "you've had $count glasses."
//            )
//        }
//
//        Button(onClick = { count++ }, modifier = Modifier.padding(top = 10.dp)) {
//            Text(text = "add data one")
//        }
//    }
//}
//
//@Composable
//fun WellnessScreen(modifier: Modifier = Modifier){
//    WaterCounter(modifier)
//}



//////////////////////////////////////////////

//@Composable
//fun WellnessTaskItem(
//    taskName : String,
//    onClose : () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Row(
//       modifier = modifier, verticalAlignment = Alignment.CenterVertically
//    ) {
//        Text(
//            modifier = Modifier
//                .weight(1f)
//                .padding(start = 16.dp),
//            text = taskName
//        )
//        IconButton(onClick = onClose) {
//            Icon(Icons.Filled.Close, contentDescription = "Close")
//        }
//    }
//}
//
//@Composable
//fun WaterCounter() {
//    Column(
//        modifier = Modifier.padding(16.dp)
//    ) {
////        var count by remember { mutableStateOf(0) }
//        var count by rememberSaveable { mutableStateOf(0) }
//
//        if(count > 0) {
//            var showTask by remember { mutableStateOf(true) }
//            if(showTask) {
//                WellnessTaskItem(
//                    taskName = "Have you taken your 15 minute walk toady?"
//                    , onClose = {
//                        showTask = false
//                        Log.e("Tag","Selected Close Button")
//                    })
//            }
//            Text(text = "You've had $count glasses")
//        }
//
//        Row(Modifier.padding(top = 8.dp)) {
//            Button(onClick = { count++ }, enabled = count < 10) {
//                Text(text = "Add one")
//            }
//
//            Button(onClick = { count = 0 }, Modifier.padding(start = 8.dp)) {
//                Text(text = "Minus one")
//            }
//        }
//    }
//}
//
//
//
@Composable
fun StateLessCounter(count : Int, onIncrement : () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        if(count > 0 ) {
            Text(text = "You've had $count glasses")
        }
        Button(onClick = onIncrement, Modifier.padding(8.dp), enabled = count < 10) {
            Text(text = "Add one")
        }
    }
}

@Composable
fun StatefulCounter(modifier: Modifier = Modifier) {
    var count by rememberSaveable { mutableStateOf(0) }
    StateLessCounter(count = count, onIncrement = { count++ }, modifier = modifier)

//    var waterCount by rememberSaveable { mutableStateOf(0) }
//    var juiceCount by rememberSaveable { mutableStateOf(0) }
//    Column {
//        StateLessCounter(count = waterCount, onIncrement = { waterCount++ }, modifier = modifier)
//        StateLessCounter(count = juiceCount, onIncrement = { juiceCount++ }, modifier = modifier)
//    }
}

@Composable
fun WellnessTaskItem(
    taskName : String,
    checked : Boolean,
    onCheckedChange : (Boolean) -> Unit,
    onClose : () -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier, verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp),
            text = taskName
        )

        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )

        IconButton(onClick = onClose) {
            Icon(Icons.Filled.Close, contentDescription = "Close")
        }
    }
}




@Composable
fun WellnessTaskItem(taskName : String, onClose: () -> Unit, modifier: Modifier = Modifier) {
    var checkedState by rememberSaveable { mutableStateOf(false) }

    WellnessTaskItem(
        taskName = taskName,
        checked = checkedState,
        onCheckedChange = { newValue -> checkedState = newValue},
        onClose = onClose,
        modifier = modifier
    )
}


@Composable
fun WellnessTasksList(modifier: Modifier = Modifier, list : List<WellnessTask>, onCloseTask : (WellnessTask) -> Unit) {
    LazyColumn(modifier = modifier) {
        items(
            items = list,
            key = { task -> task.id }
        ) { result ->
            WellnessTaskItem(taskName = result.label, onClose = {onCloseTask(result)})
        }
    }
}



@Composable
fun WellnessScreen(
    modifier: Modifier = Modifier,
    vm : WellnessViewModel = viewModel()
) {
    Column(modifier = modifier) {
        StatefulCounter()

//        //TODO : 중요
//        val list = remember {
//            getWellnessTasks().toMutableStateList()
//        }
//
//        WellnessTasksList(
//            list = list,
//            onCloseTask = { task -> list.remove(task) }
//        )

        WellnessTasksList(
            list = vm.tasks,
            onCheckedTask = { task, checked ->
                vm.changeTaskChecked(task, checked)
            },
            onCloseTask = { task ->
                vm.remove(task)
             },)

    }
}

@Composable
fun WellnessTasksList(
    list: List<WellnessTask>,
    onCheckedTask: (WellnessTask, Boolean) -> Unit,
    onCloseTask: (WellnessTask) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(
            items = list,
            key = { task -> task.id }
        ) { result ->
            WellnessTaskItem(
                taskName = result.label,
                checked = result.checked,
                onCheckedChange = { wtf ->
                    onCheckedTask(result, wtf)
                },
                onClose = { onCloseTask(result) }
            )
        }
    }
}









private fun getWellnessTasks() = List(30) {
    WellnessTask(it, "TASK INDEX : $it")
}

//체크사항
class WellnessTask(val id: Int, val label: String, initialChecked: Boolean = false) {
    var checked by mutableStateOf(initialChecked)
}




@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BasicStateCodelabTheme {
//        WaterCounter()
//        StatefulCounter()
        WellnessScreen()
    }
}