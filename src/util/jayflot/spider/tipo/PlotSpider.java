package util.jayflot.spider.tipo;
import br.blog.arruda.plot.opt.tipo.PlotType;


//pointSize: 5,
//connection: {width:5},
//active: true, 
//highlight: {mode: "area"},
//legs: { 
//  data: [
//    {label: "Efeitos Colaterais"},
//    {label: "Febre"},
//    {label: "Disuria"},
//    {label: "Diabetes"},
//    {label: "P005"},
//    {label: "P006"},
//    {label: "P007"},
//    {label: "P008"}
//    ],
//  // legScaleMax: 1,legScaleMin:0.8
//},
//scaleMode: "static" ,
//legMin: 0,
//legMax: 100,
//spiderSize: 0.5   

/**
 * PlotSpider com opções relativas ao pluging Spider (grafico de Radar/Spider)
 * @author arruda
 */
public class PlotSpider extends PlotType{

	/** ex: pointSize: 5**/
	private Double pointSize;
	
	/** ex: connection: {width:5} **/
	private SpiderConnection connection;
	
	/** active: true **/
	private Boolean active;
	
	/** highlight: {mode: "area"}**/
	private SpiderHighlight highlight;
	
	/** **/
//	private SpiderLegs legs;
	
	/** ex: static **/
	private String scaleMode;
	
	
	public class SpiderConnection{
		private Double width;
	};
	
	public class SpiderHighlight{
		/** ex: area **/
		private String mode;
	};

	public class SpiderLeg{
		/** ex: area **/
		private String data;
	};
	

	
}
