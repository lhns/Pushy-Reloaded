package de.lolhens.pushyreloaded

sealed trait Pushable {
  def isEmpty: Boolean

  def isMovableTo: Boolean

  def withoutAction: (this.type, () => Unit) = (this, () => ())

  def maybeWithAction(f: => Unit): (this.type, () => Unit) = withoutAction
}

object Pushable {

  sealed trait MovableTo extends Pushable {
    override def isMovableTo: Boolean = true

    override def maybeWithAction(f: => Unit): (MovableTo.this.type, () => Unit) = withAction(f)

    def withAction(f: => Unit): (this.type, () => Unit) = (this, () => f)
  }

  case object Empty extends MovableTo {
    override def isEmpty: Boolean = true
  }

  case object Pushable extends MovableTo {
    override def isEmpty: Boolean = false
  }

  case object Blocked extends Pushable {
    override def isEmpty: Boolean = false

    override def isMovableTo: Boolean = false
  }

  case object Solid extends Pushable {
    override def isEmpty: Boolean = false

    override def isMovableTo: Boolean = false
  }

}