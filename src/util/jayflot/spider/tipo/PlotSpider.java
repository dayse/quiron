package util.jayflot.spider.tipo;
import java.util.ArrayList;

import br.blog.arruda.plot.opt.tipo.PlotType;


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
	
	/** highlight: {mode: "area"} **/
	private SpiderHighlight highlight;
	
	/** 'legs' do grafico spider/radar **/
	private SpiderLeg legs;
	
	/** ex: scaleMode: "static" **/
	private String scaleMode;
	
	/** ex: legMin: 0 **/
	private Double legMin;
	
	/** ex: legMax: 100 **/
	private Double legMax;
	
	/** ex: spiderSize: 0.5 **/
	private Double spiderSize;
	

	public PlotSpider() {
		super();
		this.pointSize = null;
		this.connection = null;
		this.active = null;
		this.highlight = null;
		this.legs = null;
		this.scaleMode = null;
		this.legMin = null;
		this.legMax = null;
		this.spiderSize = null;
	}

	public Double getPointSize() {
		return pointSize;
	}

	public void setPointSize(Double pointSize) {
		this.pointSize = pointSize;
	}

	public SpiderConnection getConnection() {
		return connection;
	}

	public void setConnection(SpiderConnection connection) {
		this.connection = connection;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public SpiderHighlight getHighlight() {
		return highlight;
	}

	public void setHighlight(SpiderHighlight highlight) {
		this.highlight = highlight;
	}

	public SpiderLeg getLegs() {
		return legs;
	}

	public void setLegs(SpiderLeg legs) {
		this.legs = legs;
	}

	public String getScaleMode() {
		return scaleMode;
	}

	public void setScaleMode(String scaleMode) {
		this.scaleMode = scaleMode;
	}

	public Double getLegMin() {
		return legMin;
	}

	public void setLegMin(Double legMin) {
		this.legMin = legMin;
	}

	public Double getLegMax() {
		return legMax;
	}

	public void setLegMax(Double legMax) {
		this.legMax = legMax;
	}

	public Double getSpiderSize() {
		return spiderSize;
	}

	public void setSpiderSize(Double spiderSize) {
		this.spiderSize = spiderSize;
	}

	//Classes utilizadas para montar as opções mais complexas de SpiderPlot //
	public class SpiderConnection{
		private Double width;
	};
	
	public class SpiderHighlight{
		/** ex: mode: "area" **/
		private String mode;
	};

	public class SpiderLeg{
		private ArrayList<SpiderLegData> data;
		
		/**
		 * Cria um conjunto de dados das 'pernas' do grafico,
		 * dado os nomes das labels de cada uma 
		 * @param labels
		 */
		public void setData(ArrayList<String> labels){
			for (String label : labels) {
				this.data.add(new SpiderLegData(label));
			}
		}
	};

	public class SpiderLegData {
		/** ex: label: "Efeitos Colaterais" **/
		private String label;
		public SpiderLegData(String label){
			this.label = label;
		}
	};
	

	
}
