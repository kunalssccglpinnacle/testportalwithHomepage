
package com.ssccgl.pinnacle.testportal.data

import androidx.compose.runtime.Composable
import com.ssccgl.pinnacle.testportal.R

class HomeRepository {

    fun getHomeItems(): List<HomeItem> {
        return listOf(

          HomeItem(R.drawable.ic_testportal, "Test Portal"),

         //  HomeItem(ImageVector.vectorResource(id = R.drawable.dash_test_series))

        )
    }


}
