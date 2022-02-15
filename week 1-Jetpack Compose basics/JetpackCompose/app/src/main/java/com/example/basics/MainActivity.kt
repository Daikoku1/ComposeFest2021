package com.example.basics

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.basics.ui.theme.BasicsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsTheme {
                    MyApp()
            }
        }
    }
}
@Composable
fun MyApp(names: List<String> = listOf("World", "Compose")) {
    var shouldShowOnboarding by remember { mutableStateOf(true) }
    if (shouldShowOnboarding) {
        OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
    } else {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        for (name in names) {
            Greetings()
        }
    }
    }
}

@Composable
private fun OnboardingScreen(onContinueClicked: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the Basics Codelab!")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text("Continue")
        }
    }
}

@Composable
private fun Greetings(names: List<String> = List(1000) { "$it" } ) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
}

@Composable
private fun Greeting(name: String) {
    // 재구성이 일어나면 값이 새상태로 재설정 될 수 있다.
    // 재구성 상태에서 값을 유지하려면 remember를 사용해야 함
    // rememberSaveable : 화면이 돌아가거나 다크모드로 변경시에도 값 유지
    val expanded = remember { mutableStateOf(false) }
    // 애니메이션 효과 추가하기
//    val extraPadding = if (expanded.value) 48.dp else 0.dp
    val extraPadding by animateDpAsState(
        if (expanded.value) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding)
            ) {
                    Text(text = "Hello, ")
                    Text(text = name,
                        style = MaterialTheme.typography.h4.copy(
                            fontWeight = FontWeight.ExtraBold
                        )
                    )
            }
            // IconButton
            IconButton(onClick = { expanded.value = !expanded.value }) {
                Icon(
                    imageVector = if (expanded.value) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if ( expanded.value) {
                        stringResource(R.string.show_less)
                    } else {
                        stringResource(R.string.show_more)
                    }

                )
            }
            // 일반 버튼
//            OutlinedButton(
//                onClick = { expanded.value = !expanded.value }
//            ) {
//                Text(if (expanded.value) "Show less" else "Show more")
//            }

        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    BasicsTheme {
        Greetings()
    }
}

//@Composable
//fun PhotographerCard(modifier: Modifier = Modifier) {
//    // modifier 순서에 따라 문제 발생 가능성
//    // padding을 먼저 입력하면 padding이 먼저 선택되기 때문에 clickable 사용 안됨
//    Row(modifier
//        .padding(8.dp)
//        .clip(RoundedCornerShape(8.dp))
//        .background(MaterialTheme.colors.surface)
//        .clickable(onClick = { /* Ignoring onClick */ })
//        .padding(16.dp)
//    ) {
//        Surface(
//            modifier = Modifier.size(50.dp),
//            shape = CircleShape,
//            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
//        ) {
//            // Image goes here
//        }
//        Column(
//            modifier = Modifier
//                .padding(start = 8.dp)
//                .align(Alignment.CenterVertically)
//        ) {
//            Text("Alfred Sisley", fontWeight = FontWeight.Bold)
//            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
//                Text("3 minutes ago", style = MaterialTheme.typography.body2)
//            }
//        }
//    }
//}
//
//@Preview
//@Composable
//fun PhotographerCardPreview() {
//    BasicsTheme {
//        PhotographerCard()
//    }
//}