import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.BasicTextField

@Composable
fun ResponseCard(
    response: String,
    onClaimClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(0.9f)
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Issuance Initiated: ${true}",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium)
            )
            ActionButton(
                text = "Claim Credential",
                onClick = onClaimClick,
                containerColor = Color(0xFF03DAC5),
                textColor = Color.Black
            )
        }
    }
}