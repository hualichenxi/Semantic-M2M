
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
 
public class Server{
	
	//宏定义
	public static final int PEOPLE_DYNAMIC=1;
	public static final int BUS_DYNAMIC=2;
	public static final int TAXI_DYNAMIC=3;
	public static final int SENSOR_DYNAMIC=4;
	
	public static final int BUS_STATIC=5;
	public static final int RES_STATIC=6;
	
	//根据选择，产生一个三元组
	public static String generateTriple(int option) {
//		d 
//		People_ID1	trackedAt	(34,110.2)
//		Bus_ID1	trackedAt	(34,110.2)
//		Taxi_ID1	trackedAt	(34,110.2)
//		Sensor_ID1	hasValue	50
//
//		s 
//		Bus_ID1 hasDriver Driver1
//		Res_ID1	hasGeo	(34,110.2)
		
		String[] data=new String[3];
		//random函数产生随机数
		Random r = new Random();
		double longitude=0;
		double latitude=0;
		switch(option){
		case PEOPLE_DYNAMIC:
		case BUS_DYNAMIC:
		case TAXI_DYNAMIC:
			data[0]=String.format("%d", r.nextInt(100));//0~100
			data[1]="trackedAt";
			latitude=r.nextDouble()*122.12+120.51;
			longitude=r.nextDouble()*31.53+30.40;
			data[2]=String.format("(%.5f,%.5f)", longitude,latitude);
			break;
		case SENSOR_DYNAMIC:
			data[0]=String.format("%d", r.nextInt(100));
			data[1]="hasValue";
			data[2]=String.format("%d", r.nextInt(100));
			break;
		case BUS_STATIC:
			data[0]=String.format("%d", r.nextInt(100));
			data[1]="hasDriver";
			data[0]=String.format("%d", r.nextInt(100));
			break;
		case RES_STATIC:
			data[0]=String.format("%d", r.nextInt(100));
			data[1]="hasGeo";
			latitude=r.nextDouble()*122.12+120.51;
			longitude=r.nextDouble()*31.53+30.40;
			data[2]=String.format("(%.5f,%.5f)", longitude,latitude);
			break;
		default:
			data[0]="";
			data[1]="";
			data[2]="";
		}
		
		//System.out.println(data[0]+" "+data[1]+" "+data[2]);
		return data[0]+";"+data[1]+";"+data[2];
	}
	
    public static void main(String[] args) throws IOException {
    	//输入参数
    	args=new String[4];
    	args[0]="5";
    	args[1]="5";
    	args[2]="10";
    	if(args.length!=4){
			System.out.println("error!");
			System.exit(1);
		}
        int option=Integer.parseInt(args[0]);
        
        //模拟数据库，预存100条数据
        String[] staticData=new String[100];
        for(int i=0;i<100;i++){
        	 if(option==3){
        		 staticData[i]=generateTriple(BUS_STATIC);
        	 }
        	 else if(option==4){
        		 staticData[i]=generateTriple(RES_STATIC);
        	 }
        }
                
        //模拟数据流
        StringBuffer[] stringbuffer = new StringBuffer[2];//新开几个缓冲区
        for(int i=0;i<stringbuffer.length;i++){
        	stringbuffer[i]=new StringBuffer();//!!!
        }
       
        int generateT=1000/Integer.parseInt(args[2]);//一组数据的产生周期
        Timer generatTimer = new Timer();
        generatTimer.scheduleAtFixedRate(new TimerTask(){
			public void run(){
				switch(option){
				case 1:
				case 2:
				case 4:
					stringbuffer[0].append(generateTriple(PEOPLE_DYNAMIC));//将产生的数据放入缓冲区
					stringbuffer[0].append("\n");
					break;
				case 3:
					stringbuffer[0].append(generateTriple(BUS_DYNAMIC));
					stringbuffer[0].append("\n");
					break;
				case 5:
					stringbuffer[0].append(generateTriple(PEOPLE_DYNAMIC));//将产生的数据放入缓冲区
					stringbuffer[0].append("\n");
					stringbuffer[1].append(generateTriple(TAXI_DYNAMIC));//将产生的数据放入缓冲区
					stringbuffer[1].append("\n");
					break;
				case 6:
					stringbuffer[0].append(generateTriple(SENSOR_DYNAMIC));
					stringbuffer[0].append("\n");
					break;
				default:
						System.out.println("error choice!");
				}
				
			}
		}, 0, generateT);	//周期
		
		int sampleT=Integer.parseInt(args[1])*1000;//一组数据的产生周期
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask(){
				public void run(){
					for(int i=0;i<stringbuffer.length;i++){
						System.out.println("**STREAM"+i+"**");
						System.out.println(stringbuffer[i]);
						stringbuffer[i].setLength(0);//清空，用此方法效率最高
					}
				}
			}, sampleT, sampleT);
		
		
        //服务器
        ServerSocket server = new ServerSocket(8888);
		Socket client;
        OutputStream output;
        InputStream input;
        ByteArrayOutputStream byteOutput;
        while (true) {
            byteOutput = new ByteArrayOutputStream();
            client = server.accept();
            output = client.getOutputStream();
            input = client.getInputStream();
            System.out.println("handling client at:" + client.getRemoteSocketAddress());
            
            //服务器和客户端直接做一些传输
//            byte[] msg = new byte[1024];
//            int readLen = 0;
//            while ((readLen = input.read(msg)) != -1) {
//                byteOutput.write(msg, 0, readLen);
//            }
// 
//            byte[] receiveMsg = byteOutput.toByteArray();
//            System.out.println("Server receive data:" + new String(receiveMsg));
// 
//            output.write(new String("Hello Client, I am your server~").getBytes());
//            output.flush();
//            client.shutdownOutput();
// 
          input.close();
          output.close();
          client.close();
        }
    }
}