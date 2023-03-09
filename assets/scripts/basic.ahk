SendMode Input
#NoEnv
#SingleInstance Force

varToggleSpeedButton = 0

ClickMouse(x, y) {
    If  WinActive("SistLev - Escritório") || WinActive("SistLev") {
        MouseMove, x, y
        Click
        return true
    }
    return false
}

Switch_PlayPause() {
    If  WinActive("SistLev - Escritório") || WinActive("SistLev") {
        MouseMove, 675, 80
        PixelGetColor, colorPlay, 659, 66
        if (colorPlay == 0xA5542E) {
            ClickMouse(659, 67)
        }
        else {
            ClickMouse(682, 67)
        }
        return true
    }
    return false
}

;

$space::
if (!Switch_PlayPause()) {
    Send {Space}  
}
Return

$NumpadAdd::
    if (not varToggleSpeedButton = 2) {
        varToggleSpeedButton += 1
    }
    if (!ClickMouse(27*varToggleSpeedButton + 772, 67)){
        Send {NumpadAdd}
    }
Return

$NumpadSub::
    if (not varToggleSpeedButton = -2) {
        varToggleSpeedButton -= 1
    }
    if (!ClickMouse(27*varToggleSpeedButton + 772, 67)) {
        Send {NumpadSub}
    }
Return

$Left::
if (!ClickMouse(962, 65)) {
    Send {Left}
}
Return

$Right::
if (!ClickMouse(983, 65)) {
    Send {Left}
}
Return

!Left::ClickMouse(935, 65)
^Left::ClickMouse(905, 65)
+Left::ClickMouse(867, 65)

!Right::ClickMouse(1010, 65)
^Right::ClickMouse(1040, 65)
+Right::ClickMouse(1080, 65)