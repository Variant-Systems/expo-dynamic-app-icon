import ExpoModulesCore

public class ExpoDynamicAppIconModule: Module {
  public func definition() -> ModuleDefinition {
    Name("ExpoDynamicAppIcon")

    // Set app icon
    Function("setAppIcon") { (iconName: String?) -> String in
      return setAppIconWithoutAlert(iconName)
    }
    
    // Get current app icon
    Function("getAppIcon") { () -> String in
      if UIApplication.shared.supportsAlternateIcons {
        return UIApplication.shared.alternateIconName ?? "Default"
      }
      return "Default"
    }
  }
    
  private func setAppIconWithoutAlert(_ iconName: String?) -> String {
    // Check if alternate icons are supported
    guard UIApplication.shared.supportsAlternateIcons else {
      return "Error: Device doesn't support alternate icons"
    }
    
    // If icon name is same as current, return early
    if UIApplication.shared.alternateIconName == iconName {
      return "Icon is already set"
    }
    
    do {
      typealias setAlternateIconName = @convention(c) (NSObject, Selector, NSString?, @escaping (NSError?) -> ()) -> ()
      
      let selectorString = "_setAlternateIconName:completionHandler:"
      let selector = NSSelectorFromString(selectorString)
      
      guard let imp = UIApplication.shared.method(for: selector) else {
        return "Error: Method not found"
      }
      
      let method = unsafeBitCast(imp, to: setAlternateIconName.self)
      
      var resultMessage = "Icon change scheduled"
      method(UIApplication.shared, selector, iconName as NSString?, { error in
        if let error = error {
          resultMessage = "Error: \(error.localizedDescription)"
        }
      })
      
      return resultMessage
    } catch {
      return "Error: \(error.localizedDescription)"
    }
  }
}