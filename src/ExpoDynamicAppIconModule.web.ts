import { registerWebModule, NativeModule } from "expo";

class ExpoDynamicAppIconModule extends NativeModule {
  // no op
  setAppIcon() {
    return null;
  }
}

export default registerWebModule(ExpoDynamicAppIconModule);
