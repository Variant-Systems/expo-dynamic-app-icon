package expo.modules.dynamicappicon

import android.content.ComponentName
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log

object ComponentUtils {
    private const val TAG = "ExpoDynamicAppIconComponentUtils"
    
    fun doesComponentExist(packageManager: PackageManager, packageName: String, component: ComponentName): Boolean {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val flags = PackageManager.PackageInfoFlags.of(
                    (PackageManager.GET_ACTIVITIES or PackageManager.MATCH_DISABLED_COMPONENTS).toLong()
                )
                val packageInfo = packageManager.getPackageInfo(packageName, flags)
                val activityExists = packageInfo.activities?.any { it.name == component.className } ?: false
                Log.d(TAG, "Component exists: ${component.className} -> $activityExists")
                activityExists
            } else {
                // For older Android versions, use MATCH_DISABLED_COMPONENTS instead of GET_DISABLED_COMPONENTS
                @Suppress("DEPRECATION")
                val packageInfo = packageManager.getPackageInfo(
                    packageName,
                    PackageManager.GET_ACTIVITIES or PackageManager.MATCH_DISABLED_COMPONENTS
                )
                val activityExists = packageInfo.activities?.any { it.name == component.className } ?: false
                Log.d(TAG, "Component exists: ${component.className} -> $activityExists")
                activityExists
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error checking component existence for ${component.className}", e)
            false
        }
    }
}