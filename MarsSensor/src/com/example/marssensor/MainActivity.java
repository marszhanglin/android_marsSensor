package com.example.marssensor;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
/**
 * �����������ؼ���1����ȡ����������SensorManager  2����onResume������ע��ĳ�ִ������ļ���      3����onSensorChanged��ȡ����
 * ��Ϊ�����ܿ��ǻ�Ҫ��onstop��onpause������
 * 2014-10-27����8:52:54 ��MainActivity
 * @author Mars zhang
 *
 */
public class MainActivity extends Activity implements SensorEventListener{
	/**������������*/
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
		//��ȡ�������������SENSOR_SERVICE
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
	protected void onResume() {//����Ϊʲô����TYPE_ALL������
		super.onResume();
		// ע�᷽�򴫸�������
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
				sensorManager.SENSOR_DELAY_UI);
		// ע��ų�����������
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
				sensorManager.SENSOR_DELAY_UI);
		// ע���¶ȴ���������
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE),
				sensorManager.SENSOR_DELAY_UI);
		// ע��⴫��������
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
				sensorManager.SENSOR_DELAY_UI);
		// ע����������������
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				sensorManager.SENSOR_DELAY_UI);
		// ע��ѹ������������
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE),
				sensorManager.SENSOR_DELAY_UI);
	}
	//��ͣʱ
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
	//��������ȡֵ�ĵط�
	@Override
	public void onSensorChanged(SensorEvent event) {
		//��ȡ��������������ֵ   ��ͬ��������ֵ�ǲ�һ����
		float[] values= event.values;
		int sensorType=event.sensor.getType();
		StringBuilder builder=new StringBuilder();
		switch (sensorType) {
		case Sensor.TYPE_ORIENTATION: //���򴫸���
			builder.append("======���򴫸�����ORIENTATION========\n");
			builder.append("���򴫸���values[0]  "+values[0]+"\n��zΪת������0��(360)����90��\n");  //ˮƽ0~360 ��λ��
			builder.append("���򴫸���values[1]  "+values[1]+"\n��xΪת��(-180~180��Ļ����Ϊ180��(-180��))\n");
			builder.append("���򴫸���values[2]  "+values[2]+"\n��yΪת��(- 90~ 90��Ļ����Ϊ   90��(- 90��))\n");
			sensor_value_ORIENTATION.setText(builder.toString());
			break; 
		case Sensor.TYPE_MAGNETIC_FIELD: //�ų�������
			builder.append("======�ų���������MAGNETIC_FIELD======\n");
			builder.append("�ų�������values[0]  "+values[0]+"\nx\n");   
			builder.append("�ų�������values[1]  "+values[1]+"\ny\n");
			builder.append("�ų�������values[2]  "+values[2]+"\nz\n");
			sensor_value_MAGNETIC_FIELD.setText(builder.toString());
			break; 
		case Sensor.TYPE_AMBIENT_TEMPERATURE: //�¶ȴ�����
			builder.append("======�¶ȴ�������AMBIENT_TEMPERATURE======\n");
			builder.append("�¶ȴ�����values[0]  "+values[0]+"\n���϶�(��)\n");   
			sensor_value_AMBIENT_TEMPERATURE.setText(builder.toString());
			break;
		case Sensor.TYPE_LIGHT: //�⴫����
			builder.append("======�⴫������LIGHT======\n"); 
			builder.append("�⴫����values[0]  "+values[0]+"\n�տ�˹(lx)\n");  //ˮƽ0~360 ��λ�� 
			sensor_value_LIGHT.setText(builder.toString()); 
			break;
		case Sensor.TYPE_ACCELEROMETER: //����������
			builder.append("======������������MAGNETIC_PRESSURE===\n");
			builder.append("����������values[0]  "+values[0]+"\nx����������ٶ�\n");  //ˮƽ0~360 ��λ��
			builder.append("����������values[1]  "+values[1]+"\ny����������ٶ�\n");
			builder.append("����������values[2]  "+values[2]+"\nz����������ٶ�\n");
			sensor_value_ACCELEROMETER.setText(builder.toString());
			break;
		case Sensor.TYPE_PRESSURE: //ѹ��������
			builder.append("======ѹ����������MAGNETIC_PRESSURE======\n");
			builder.append("ѹ��������values[0]  "+values[0]+"\n����ѹ��(����)\n");  //ˮƽ0~360 ��λ�� 
			sensor_value_PRESSURE.setText(builder.toString());
			break;
		default:
			other.setText("�������ֻ����������Ĵ�����==>"+sensorType);
			break;
		}
	}
	//���ȸı�ʱ�Ļص�����  Ŀǰ����֪�����������
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
	}
}


//===============values���ĵ�===TYPE_ORIENTATION:����=================================================//
//values[0]: Azimuth, angle between the magnetic north direction and the y-axis, 
//around the z-axis (0 to 359). 0=North, 90=East, 180=South, 270=West 
//
//values[1]: Pitch, rotation around x-axis (-180 to 180), with positive values 
//when the z-axis moves toward the y-axis. 
//
//values[2]: Roll, rotation around y-axis (-90 to 90), with positive values when 
//the x-axis moves toward the z-axis. 
//===============values���ĵ�=====================================================//
