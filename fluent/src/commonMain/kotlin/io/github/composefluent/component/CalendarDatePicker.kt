package io.github.composefluent.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.github.composefluent.ExperimentalFluentApi

/**
 * A calendar date picker that allows the user to select a single date from a calendar view within a flyout.
 *
 * This composable provides a button that, when clicked, opens a flyout containing a [CalendarViewLayout].
 * The user can navigate through months, years, and decades within the calendar to select a specific date.
 *
 * Once a date is selected, the flyout closes, the selected date is displayed on the button, and the `onChoose` callback is invoked.
 *
 * The date displayed in the button will be shown in the format: "year/month/day".
 *
 * @param onChoose Callback invoked when a date is selected by the user. Receives the selected [CalendarDatePickerState.Day].
 * @param state The state of the calendar date picker, allowing customization of the displayed calendar.
 */
@Composable
@ExperimentalFluentApi
fun CalendarDatePicker(
    onChoose: (CalendarDatePickerState.Day) -> Unit,
    state: CalendarDatePickerState = remember { CalendarDatePickerState() }
) {
    var day by remember { mutableStateOf<CalendarDatePickerState.Day?>(null) }
    BasicFlyoutContainer(
        flyout = {
            BasicFlyout(
                visible = isFlyoutVisible,
                onDismissRequest = { isFlyoutVisible = false },
                contentPadding = PaddingValues()
            ) {
                CalendarViewLayout(
                    onChoose = {
                        day = it
                        onChoose(it)
                        isFlyoutVisible = false
                    },
                    state = state
                )
            }
        },
        content = {
            Button(
                onClick = {
                    isFlyoutVisible = true
                },
                content = {
                    Text(
                        day?.let { day ->
                            "${day.year}/${day.monthValue + 1}/${day.day}"
                        } ?: "Pick a date"
                    )
                    FontIcon(type = FontIconPrimitive.Calendar, contentDescription = null)
                }
            )
        }
    )
}
