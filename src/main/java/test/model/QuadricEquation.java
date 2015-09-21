package test.model;

public class QuadricEquation {
	public Double _A;
	public Double _B;
	public Double _C;
	
	public Double _D;
	public Double _X1;
	public Double _X2;
	
	public QuadricEquation() {
		this(0.0, 0.0, 0.0);
	}

	public QuadricEquation(Double a, Double b, Double c) {
		this._A = a;
		this._B = b;
		this._C = c;
	}

	
	public void calculate(){
		System.out.println("calculate");
		_D = calcD();
		_X1 = (-_B + _D) / 2;
		_X2 = (-_B - _D) / 2;
	}
	

	public void setData(double a, double b, double c) {
		this._A = a;
		this._B = b;
		this._C = c;
	}
	
	
	private Double calcD(){
		return Math.sqrt(Math.pow(_B, 2) - 4 * _A * _C);
	}
}

