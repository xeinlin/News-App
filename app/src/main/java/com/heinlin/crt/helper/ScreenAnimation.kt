package com.heinlin.crt.helper

import androidx.annotation.AnimRes
import com.heinlin.crt.R

enum class ScreenAnimation(
    @AnimRes val enter: Int,
    @AnimRes val exit: Int,
    @AnimRes val popEnter: Int,
    @AnimRes val popExit: Int
) {
    DEFAULT(
        enter = R.anim.slide_in_right,
        exit = R.anim.slide_out_left,
        popEnter = R.anim.slide_in_left,
        popExit = R.anim.slide_out_right
    ),
    ENTER_RIGHT_EXIT_RIGHT(
        enter = R.anim.slide_in_right,
        exit = R.anim.slide_out_left,
        popEnter = R.anim.slide_in_left,
        popExit = R.anim.slide_out_right
    ),
    ENTER_LEFT_EXIT_LEFT(
        enter = R.anim.slide_in_left,
        exit = R.anim.slide_out_right,
        popEnter = R.anim.slide_in_right,
        popExit = R.anim.slide_out_left
    ),
    ENTER_UP_EXIT_UP(
        enter = R.anim.push_up_in,
        exit = R.anim.push_up_out,
        popEnter = R.anim.push_down_in,
        popExit = R.anim.push_down_out
    ),
    ENTER_UP_EXIT_STAY(
        enter = R.anim.push_up_in,
        exit = R.anim.stay,
        popEnter = R.anim.stay,
        popExit = R.anim.push_down_out
    ),
    ENTER_DOWN_EXIT_DOWN(
        enter = R.anim.push_down_in,
        exit = R.anim.push_down_out,
        popEnter = R.anim.push_up_in,
        popExit = R.anim.push_up_out
    ),
    FADE_IN_FADE_OUT(
        enter = R.anim.fade_in,
        exit = R.anim.fade_out,
        popEnter = R.anim.fade_in,
        popExit = R.anim.fade_out
    ),
    STAY_IN_STAY_OUT(
        enter = R.anim.stay,
        exit = R.anim.stay,
        popEnter = R.anim.stay,
        popExit = R.anim.stay
    ),
    SLIGHTLY_ENTER_RIGHT_EXIT_RIGHT(
        enter = R.anim.slide_in_right,
        exit = R.anim.slightly_slide_out_left,
        popEnter = R.anim.slightly_slide_in_left,
        popExit = R.anim.slide_out_right
    ),
    SLIGHTLY_ENTER_LEFT_EXIT_LEFT(
        enter = R.anim.slide_in_left,
        exit = R.anim.slightly_slide_out_right,
        popEnter = R.anim.slightly_slide_in_right,
        popExit = R.anim.slide_out_left
    ),
    SLIGHTLY_ENTER_UP_EXIT_UP(
        enter = R.anim.push_up_in,
        exit = R.anim.slightly_push_up_out,
        popEnter = R.anim.slightly_push_down_in,
        popExit = R.anim.push_down_out
    ),
    SLIGHTLY_ENTER_DOWN_EXIT_DOWN(
        enter = R.anim.push_down_in,
        exit = R.anim.slightly_push_down_out,
        popEnter = R.anim.slightly_push_up_in,
        popExit = R.anim.push_up_out
    )
}