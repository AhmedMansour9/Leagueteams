package com.leagueteams

import org.greenrobot.eventbus.EventBus



class GlobalBus {

    companion object {
        private var sBus: EventBus? = null

    fun getBus(): EventBus? {
        if (sBus == null)
            sBus = EventBus.getDefault()
        return sBus
    }
}

}