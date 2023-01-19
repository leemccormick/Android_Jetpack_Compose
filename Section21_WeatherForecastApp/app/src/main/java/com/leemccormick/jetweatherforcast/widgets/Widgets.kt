package com.leemccormick.jetweatherforcast.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.leemccormick.jetweatherforcast.R
import com.leemccormick.jetweatherforcast.model.WeatherItem
import com.leemccormick.jetweatherforcast.utils.formatDate
import com.leemccormick.jetweatherforcast.utils.formatDateTime
import com.leemccormick.jetweatherforcast.utils.formatDecimals


@Composable
fun WeatherDetailRow(weather: WeatherItem) {
    val imageUrl = "https://openweathermap.org/img/wn/${weather.weather[0].icon}.png"

    Surface(
        Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        shape = CircleShape.copy(topEnd = CornerSize(6.dp)),
        color = Color.White
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                formatDate(weather.dt).split(",")[0],
                modifier = Modifier.padding(5.dp)
            )

            WeatherStateImage(imageUrl = imageUrl)

            Surface(
                modifier = Modifier.padding(0.dp),
                shape = CircleShape,
                color = Color(0xFFFFC400)
            ) {
                Text(
                    weather.weather[0].description,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.caption
                )
            }

            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color.Blue.copy(alpha = 0.7f),
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append(formatDecimals(weather.temp.max) + "º")
                    }

                    withStyle(
                        style = SpanStyle(color = Color.LightGray)
                    ) {
                        append(formatDecimals(weather.temp.min) + "º")
                    }
                }
            )
        }
    }
}

@Composable
fun SunRiseAndSunSetRow(weather: WeatherItem) {
    Row(
        modifier = Modifier
            .padding(top = 15.dp, bottom = 6.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.sunrise),
                contentDescription = "sunrise icon",
                modifier = Modifier.size(30.dp)
            )

            Text(
                text = formatDateTime(weather.sunrise),
                style = MaterialTheme.typography.caption
            )
        }

        Row(modifier = Modifier.padding(4.dp)) {
            Text(
                text = formatDateTime(weather.sunset),
                style = MaterialTheme.typography.caption
            )

            Icon(
                painter = painterResource(id = R.drawable.sunset),
                contentDescription = "sunrise icon",
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
fun HumidityWindPressureRow(weather: WeatherItem, isImperial: Boolean) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = "humidity icon",
                modifier = Modifier.size(20.dp)
            )

            Text(
                text = "${weather.humidity}%",
                style = MaterialTheme.typography.caption
            )
        }

        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.pressure),
                contentDescription = "pressure icon",
                modifier = Modifier.size(20.dp)
            )

            Text(
                text = "${weather.pressure} psi",
                style = MaterialTheme.typography.caption
            )
        }

        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.wind),
                contentDescription = "wind icon",
                modifier = Modifier.size(20.dp)
            )

            Text(
                text = "${formatDecimals(weather.speed)}" + if (isImperial) " mph" else " m/s",
                style = MaterialTheme.typography.caption
            )
        }
    }
}

@Composable
fun WeatherStateImage(imageUrl: String) {
    Image(
        painter = rememberImagePainter(imageUrl),
        contentDescription = "icon image",
        modifier = Modifier.size(80.dp)
    )
}
