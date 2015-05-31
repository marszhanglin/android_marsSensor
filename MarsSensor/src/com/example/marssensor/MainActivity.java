package com.example.marssensor;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
/**
 * 传感器三个关键点1、获取传感器服务SensorManager  2、在onResume方法里注册某种传感器的监听      3、在onSensorChanged获取数据
 * （为了性能考虑还要在onstop与onpause方法）
 * 2014-10-27上午8:52:54 类MainActivity
 * @author Mars zhang
 *
 */
public class MainActivity extends Activity implements SensorEventListener{
	/**传感器管理器*/
	SensorManager sensorManager=null;
	private TextView sensor_value_ORIENTATION;    //sensor_value
	private TextView sensor_value_MAGNETIC_FIELD;  //sensor_value_cc
	private TextView sensor_value_AMBIENT_TEMPERATURE;    //sensor_value_wd
	private TextView sensor_value_LIGHT;                 //sensor_value_g
	private TextView sensor_value_ACCELEROMETER;    //sensor_value_zl
	private TextView sensor_value_PRESSURE;  //sensor_value_yl
	private TextView other;  //activity_main_other
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		//获取传感器管理服务SENSOR_SERVICE
		sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
		
	} 
	
	private void initView() {
		sensor_value_ORIENTATION=(TextView) findViewById(R.id.sensor_value);
		sensor_value_MAGNETIC_FIELD=(TextView) findViewById(R.id.sensor_value_cc);
		sensor_value_AMBIENT_TEMPERATURE=(TextView) findViewById(R.id.sensor_value_wd);
		sensor_value_LIGHT=(TextView) findViewById(R.id.sensor_value_g);
		sensor_value_ACCELEROMETER=(TextView) findViewById(R.id.sensor_value_zl);
		sensor_value_PRESSURE=(TextView) findViewById(R.id.sensor_value_yl);
		other=(TextView) findViewById(R.id.activity_main_other);
	}

	@Override
	protected void onResume() {//老子为什么不用TYPE_ALL？？？
		super.onResume();
		// 注册方向传感器监听
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
				sensorManager.SENSOR_DELAY_UI);
		// 注册磁场传感器监听
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
				sensorManager.SENSOR_DELAY_UI);
		// 注册温度传感器监听
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE),
				sensorManager.SENSOR_DELAY_UI);
		// 注册光传感器监听
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
				sensorManager.SENSOR_DELAY_UI);
		// 注册重力传感器监听
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				sensorManager.SENSOR_DELAY_UI);
		// 注册压力传感器监听
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE),
				sensorManager.SENSOR_DELAY_UI);
	}
	//暂停时
	@Override
	protected void onPause() {
		sensorManager.unregisterListener(this);
		super.onPause();
	}
	@Override
	protected void onStop() {
		sensorManager.unregisterListener(this);
		super.onStop();
	}
	//传感器获取值的地方
	@Override
	public void onSensorChanged(SensorEvent event) {
		//获取传感器个个参数值   不同传感器的值是不一样的
		float[] values= event.values;
		int sensorType=event.sensor.getType();
		StringBuilder builder=new StringBuilder();
		switch (sensorType) {
		case Sensor.TYPE_ORIENTATION: //方向传感器
			builder.append("======方向传感器：ORIENTATION========\n");
			builder.append("方向传感器values[0]  "+values[0]+"\n以z为转轴正北0度(360)正东90度\n");  //水平0~360 方位角
			builder.append("方向传感器values[1]  "+values[1]+"\n以x为转轴(-180~180屏幕向下为180度(-180度))\n");
			builder.append("方向传感器values[2]  "+values[2]+"\n以y为转轴(- 90~ 90屏幕向下为   90度(- 90度))\n");
			sensor_value_ORIENTATION.setText(builder.toString());
			break; 
		case Sensor.TYPE_MAGNETIC_FIELD: //磁场传感器
			builder.append("======磁场传感器：MAGNETIC_FIELD======\n");
			builder.append("磁场传感器values[0]  "+values[0]+"\nx\n");   
			builder.append("磁场传感器values[1]  "+values[1]+"\ny\n");
			builder.append("磁场传感器values[2]  "+values[2]+"\nz\n");
			sensor_value_MAGNETIC_FIELD.setText(builder.toString());
			break; 
		case Sensor.TYPE_AMBIENT_TEMPERATURE: //温度传感器
			builder.append("======温度传感器：AMBIENT_TEMPERATURE======\n");
			builder.append("温度传感器values[0]  "+values[0]+"\n摄氏度(℃)\n");   
			sensor_value_AMBIENT_TEMPERATURE.setText(builder.toString());
			break;
		case Sensor.TYPE_LIGHT: //光传感器
			builder.append("======光传感器：LIGHT======\n"); 
			builder.append("光传感器values[0]  "+values[0]+"\n勒克斯(lx)\n");  //水平0~360 方位角 
			sensor_value_LIGHT.setText(builder.toString()); 
			break;
		case Sensor.TYPE_ACCELEROMETER: //重力传感器
			builder.append("======重力传感器：MAGNETIC_PRESSURE===\n");
			builder.append("重力传感器values[0]  "+values[0]+"\nx轴的重力加速度\n");  //水平0~360 方位角
			builder.append("重力传感器values[1]  "+values[1]+"\ny轴的重力加速度\n");
			builder.append("重力传感器values[2]  "+values[2]+"\nz轴的重力加速度\n");
			sensor_value_ACCELEROMETER.setText(builder.toString());
			break;
		case Sensor.TYPE_PRESSURE: //压力传感器
			builder.append("======压力传感器：MAGNETIC_PRESSURE======\n");
			builder.append("压力传感器values[0]  "+values[0]+"\n大气压力(毫巴)\n");  //水平0~360 方位角 
			sensor_value_PRESSURE.setText(builder.toString());
			break;
		default:
			other.setText("吊！该手机还有其他的传感器==>"+sensorType);
			break;
		}
	}
	//精度改变时的回调方法  目前还不知道用在哪里？？
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
	}
}


//===============values的文档===TYPE_ORIENTATION:方向=================================================//
//values[0]: Azimuth, angle between the magnetic north direction and the y-axis, 
//around the z-axis (0 to 359). 0=North, 90=East, 180=South, 270=West 
//
//values[1]: Pitch, rotation around x-axis (-180 to 180), with positive values 
//when the z-axis moves toward the y-axis. 
//
//values[2]: Roll, rotation around y-axis (-90 to 90), with positive values when 
//the x-axis moves toward the z-axis. 
//===============values的文档=====================================================//
