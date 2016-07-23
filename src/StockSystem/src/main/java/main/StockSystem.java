package main;

import exception.StatusNotOKException;
import javafx.application.Platform;
import logic.Initializebl.InitializeInDatabeseLogic;
import logicservice.showInfoblservice.initialLogicservice;
import presentation.main.MainController;
import utils.NetStatus;

/**
 * @Description: 整个项目的启动类
 * 
 * @version V1.0
 */
public class StockSystem {

	public static void main(String[] args) {
		initSettings();
		MainController.launch();
		if (NetStatus.isConnected()) {
			initialLogicservice initial = new InitializeInDatabeseLogic();
			try {
				initial.updateDatabese();
				System.out.println("already update");
			} catch (StatusNotOKException e) {
				System.out.println("初始化异常");
				e.printStackTrace();
			}
			System.out.println("end");
		}
		
	}
	
	/**
	 * 初始化之前的设置
	 */
	public static void initSettings(){
		Platform.setImplicitExit(false);
	}

}
