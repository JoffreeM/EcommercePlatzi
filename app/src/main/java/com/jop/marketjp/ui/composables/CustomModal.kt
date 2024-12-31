package com.jop.marketjp.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jop.marketjp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomModalConfirmation(
    showModal: Boolean,
    title: String = "",
    body: String = "",
    onAccept: () -> Unit = {},
    onCancel: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    if (showModal) {
        BasicAlertDialog(onDismissRequest = {
            onDismiss()
        }
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = 10.dp,
                shape = RoundedCornerShape(20.dp),
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(top = 25.dp)
                        .padding(bottom = 25.dp)
                ) {
                    CustomSpace(height = 20)
                    if (title.isNotEmpty()) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 2.dp),
                            text = title,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray,
                            textAlign = TextAlign.Center,
                            maxLines = 2
                        )
                    }
                    if (body.isNotEmpty()) {
                        CustomSpace(height = 20)
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = body,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                    CustomSpace(height = 30)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        CustomButton(
                            text = R.string.cart_shopping_modal_btn_accept,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 6.dp)
                                .weight(1f),
                            onClick = {
                                onAccept()
                            }
                        )
                        CustomButton(
                            text = R.string.cart_shopping_modal_btn_cancel,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 6.dp)
                                .weight(1f),
                            onClick = {
                                onCancel()
                            }
                        )
                    }
                    CustomSpace(height = 6)
                }
            }
        }
    }
}

