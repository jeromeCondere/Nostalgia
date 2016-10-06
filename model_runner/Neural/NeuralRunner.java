package model_runner.Neural;

import java.util.ArrayList;

import utils.Neural.NeuralData;

import model_runner.ModelRunner;

public class NeuralRunner extends ModelRunner {

	//a classic sum function return the linear combinaison
	public NeuralData sum(ArrayList<NeuralData> datum)
	{
		float NewValue=0;
		String content="";
		for(NeuralData d:datum)
		{
			NewValue+=d.getWeight()*d.getValue();
			content+=d.getContent()+"\n\n";
			
		}
		NeuralData data=new NeuralData(null, content, NewValue,0);
		return data;
		
	}
	//heaviside
	public NeuralData transfert(NeuralData data,float bias)
	{
		if(data.getValue()>bias)
		{
			return data;
		}
		else
		{
			data.setValue(0);
			return data;
		}
		
	}
	
}
