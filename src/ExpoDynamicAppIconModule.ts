import { NativeModule, requireNativeModule } from "expo";

declare class ExpoDynamicAppIconModule extends NativeModule {
  setAppIcon(icon: string): void;
}

export default requireNativeModule<ExpoDynamicAppIconModule>(
  "ExpoDynamicAppIcon"
);
