package com.arcusys.valamis.certificate.storage.schema

import com.arcusys.valamis.certificate.model.goal.ActivityGoal
import com.arcusys.valamis.persistence.common.DbNameUtils._
import com.arcusys.valamis.persistence.common.SlickProfile

trait ActivityGoalTableComponent
  extends CertificateTableComponent
    with CertificateGoalTableComponent { self: SlickProfile =>

  import driver.simple._

  class ActivityGoalTable(tag: Tag) extends Table[ActivityGoal](tag, tblName("CERT_GOALS_ACTIVITY")) {
    def goalId = column[Long]("GOAL_ID")
    def certificateId = column[Long]("CERTIFICATE_ID")
    def activityName = column[String]("ACTIVITY_NAME", O.Length(254, true))
    def count = column[Int]("COUNT")

    def * = (goalId, certificateId, activityName, count) <> (ActivityGoal.tupled, ActivityGoal.unapply)

    def PK = primaryKey(pkName("CERT_GOALS_ACTIVITY"), (goalId, certificateId))
    def certificateFK = foreignKey(fkName("GOALS_ACTIVITY_TO_CERT"), certificateId, TableQuery[CertificateTable])(x => x.id, onDelete = ForeignKeyAction.NoAction)
    def goalFK = foreignKey(fkName("GOALS_ACTIVITY_TO_GOAL"), goalId, certificateGoals)(x => x.id, onDelete = ForeignKeyAction.Cascade)
  }

  val activityGoals = TableQuery[ActivityGoalTable]
}