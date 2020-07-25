package de.lolhens.pushyreloaded

import de.lolhens.pushyreloaded.Attributes.Attribute

class Attributes private(attributes: Map[Attribute[_], _]) {
  private def withAttributes(attributes: Map[Attribute[_], _]) = new Attributes(attributes)

  def get[A](key: Attribute[A]): A = attributes.get(key).map(_.asInstanceOf[A]).getOrElse(key.orElse())

  def put[A](key: Attribute[A])(value: A): Attributes = withAttributes(attributes + (key -> value))

  def map[A](key: Attribute[A])(f: A => A): Attributes = put(key)(f(get(key)))

  def remove[A](key: Attribute[A]): Attributes = withAttributes(attributes - key)
}

object Attributes {
  def apply(): Attributes = new Attributes(Map.empty)

  case class Attribute[A](name: String, orElse: () => A)

  trait WithAttributes {
    private var _attributes = Attributes()

    def attributes: Attributes = _attributes

    def changeAttributes(f: Attributes => Attributes): Unit =
      _attributes = f(_attributes)
  }

}
