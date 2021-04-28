package frc.robot.sensors;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class ColorSensor extends SubsystemBase {
    
    private final AnalogInput m_colorSensorColor = new AnalogInput(6);
    private final AnalogInput m_colorSensorBrightness = new AnalogInput(2);  

    public int getColorSensorColor() {
        if (m_colorSensorColor != null) {
            return m_colorSensorColor.getChannel();
          }
      
          return 0;
    }

    public int getColorSensorBrightness() {
        if (m_colorSensorBrightness != null) {
            return m_colorSensorBrightness.getChannel();
          }
      
          return 0;
    } 
}
