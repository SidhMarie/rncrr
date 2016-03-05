package rncrr.llt.model.process.dsp;

public class Complex {

    private final double re;   // the real part
    private final double im;   // the imaginary part

    public Complex(){
        this.re = 0.0;
        this.im = 0.0;
    }

    public Complex(Complex complex) {
        this.re = complex.re;
        this.im = complex.im;
    }

    public Complex(double real, double imag) {
        this.re = real;
        this.im = imag;
    }

    public double re() { return this.re; }
    public double im() { return this.im; }

    public String toString() {
        if (im == 0) return re + "";
        if (re == 0) return im + "i";
        if (im <  0) return re + " - " + (-im) + "i";
        return re + " + " + im + "i";
    }

    // return abs/modulus/magnitude and angle/phase/argument
    public double abs()   { return Math.hypot(re, im); }  // Math.sqrt(re*re + im*im) - амплитуда
    public double power() {return ((re*re) + (im*im)); }  // spectrum of power - спектр мощности сигнала
    public double phase() { return Math.atan2(im, re); }  // between -pi and pi - фаза

    // return a new Complex object whose value is (this + b)
    public Complex plus(Complex b) {
        double real = this.re + b.re;
        double imag = this.im + b.im;
        return new Complex(real, imag);
    }

    // return a new Complex object whose value is (this - b)
    public Complex minus(Complex b) {
        double real = this.re - b.re;
        double imag = this.im - b.im;
        return new Complex(real, imag);
    }

    // return a new Complex object whose value is (this * b)
    public Complex times(Complex b) {
        double real = this.re * b.re - this.im * b.im;
        double imag = this.re * b.im + this.im * b.re;
        return new Complex(real, imag);
    }

    // scalar multiplication
    // return a new object whose value is (this * alpha)
    public Complex times(double alpha) {
        return new Complex(alpha * re, alpha * im);
    }

    // return a new Complex object whose value is the conjugate of this
    public Complex conjugate() {  return new Complex(re, -im); }

    // return a new Complex object whose value is the reciprocal of this
    public Complex reciprocal() {
        double scale = re*re + im*im;
        return new Complex(re / scale, -im / scale);
    }

    // return a / b
    public Complex divides(Complex b) {
        return this.times(b.reciprocal());
    }

    // return a new Complex object whose value is the complex exponential of this
    public Complex exp() {
        return new Complex(Math.exp(re) * Math.cos(im), Math.exp(re) * Math.sin(im));
    }

    // return a new Complex object whose value is the complex sine of this
    public Complex sin() {
        return new Complex(Math.sin(re) * Math.cosh(im), Math.cos(re) * Math.sinh(im));
    }

    // return a new Complex object whose value is the complex cosine of this
    public Complex cos() {
        return new Complex(Math.cos(re) * Math.cosh(im), -Math.sin(re) * Math.sinh(im));
    }

    // return a new Complex object whose value is the complex tangent of this
    public Complex tan() {
        return sin().divides(cos());
    }

    public static Complex plus(Complex a, Complex b) {
        return new Complex(a).plus(b);
    }

    public static Complex minus(Complex a, Complex b) {
        return new Complex(a).minus(b);
    }

    public static Complex times(Complex a, Complex b) {
        return new Complex(a).times(b);
    }

    public static Complex times(Complex a, double alpha) {
        return new Complex(a).times(alpha);
    }

    public static Complex conjugate(Complex a) {
        return new Complex(a).conjugate();
    }

    public static Complex reciprocal(Complex a) {
        return new Complex(a).reciprocal();
    }

    public static Complex divides(Complex a, Complex b) {
        return new Complex(a).divides(b);
    }


}
