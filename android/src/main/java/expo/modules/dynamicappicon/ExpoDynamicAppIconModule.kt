package expo.modules.dynamicappicon

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition

class ExpoDynamicAppIconModule : Module() {
    companion object {
        private const val TAG = "ExpoDynamicAppIconModule"
    }

    override fun definition() = ModuleDefinition {
        Name("ExpoDynamicAppIcon")

        Function("setAppIcon") { name: String ->
            try {
                val newIcon = "${context.packageName}.MainActivity$name"
                val defaultIconName = "${context.packageName}.MainActivity"

                val currentIcon = getCurrentIcon()
                if (newIcon == currentIcon) {
                    Log.d(TAG, "Icon is already set")
                    return@Function "Icon is already set"
                }

                // Validate if the new icon exists
                if (!ComponentUtils.doesComponentExist(
                        context.packageManager,
                        context.packageName,
                        ComponentName(context.packageName, newIcon)
                )) {
                    // Falling back to default icon
                    Log.w(TAG, "Falling back to default icon: $defaultIconName")
                    SharedObject.icon = defaultIconName  
                } else {
                    SharedObject.icon = newIcon
                }

                // Schedule the icon change
                SharedObject.packageName = context.packageName
                SharedObject.pm = packageManager
                SharedObject.classesToKill.add(currentIcon)

                Log.d(TAG, "Icon change scheduled")
                return@Function "Icon change scheduled"
            } catch (e: Exception) {
                Log.e(TAG, "Error in setAppIcon", e)
                return@Function "Error: ${e.message}"
            }
        }

        Function("getAppIcon") {
            val currentIcon = getCurrentIcon()
            val iconName = currentIcon.split("MainActivity").getOrNull(1) ?: "Default"
            return@Function iconName
        }
    }

    private val context: Context
        get() = requireNotNull(appContext.reactContext) { "React Application Context is null" }

    private val packageManager: PackageManager
        get() = requireNotNull(context.packageManager) { "Package Manager is null" }

    
    private fun getCurrentIcon(): String {
        return try {
            val packageInfo = packageManager.getPackageInfo(
                context.packageName,
                PackageManager.GET_ACTIVITIES
            )
            
            packageInfo.activities?.firstOrNull {
                packageManager.getComponentEnabledSetting(
                    ComponentName(context.packageName, it.name)
                ) == PackageManager.COMPONENT_ENABLED_STATE_ENABLED
            }?.name ?: "${context.packageName}.MainActivity"
        } catch (e: Exception) {
            Log.e(TAG, "Error getting current icon", e)
            "${context.packageName}.MainActivity"
        }
    }
}