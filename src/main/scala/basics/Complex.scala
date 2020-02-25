package basics

case class Complex(r: Double, i: Double) {
  def +(that: Complex) = Complex(r + that.r, i + that.i)
  def -(that: Complex) = Complex(r - that.r, i - that.i)
  def *(that: Complex) = Complex(r * that.r - i * that.i, r * that.i + i * that.r)
  def magSqr = r * r + i * i
  def magnitude = math.sqrt(magSqr)
}