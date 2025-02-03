package expo.modules.dynamicappicon

import android.content.Context
import expo.modules.core.interfaces.Package
import expo.modules.core.interfaces.ApplicationLifecycleListener

class ExpoDynamicAppIconPackage : Package {
    override fun createApplicationLifecycleListeners(context: Context): List<ApplicationLifecycleListener> {
        return listOf(ExpoDynamicAppIconApplicationLifecycleListener())
    }
}