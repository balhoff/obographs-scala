import spray.json._
import DefaultJsonProtocol._
import org.phenoscape.scowl._
import org.semanticweb.owlapi.model._

final object MyTypes {
  type Label = Option[String]
  type ID = String
}
import MyTypes._

case class BasicPropertyValue (
  pred : Option[ID] = None,
  value : Option[String] = None,
  meta : Option[Meta] = None,
  xrefs : Option[List[ID]] = None
)
case class SynonymPropertyValue (
  pred : Option[ID] = None,
  value : Option[String] = None,
  meta : Option[Meta] = None,
  xrefs : Option[List[ID]] = None
)
case class Xref (
  value : ID,
  lbl : Label = None,
  meta : Option[Meta] = None
)

case class Meta(
  definition: Option[String] = None,
  comments : Option[List[String]] = None,
  subsets : Option[List[ID]] = None,
  synonyms : Option[List[SynonymPropertyValue]] = None,
  xrefs : Option[List[Xref]] = None,
  basicPropertyValues : Option[List[BasicPropertyValue]] = None,
  version : Option[String] = None,
  deprecated : Option[Boolean] = None
)


case class Node(
  id: ID, 
  lbl: Label = None,
  meta: Option[Meta] = None
)
case class Edge(
  sub: ID, 
  pred: ID,
  obj: ID,
  meta: Option[Meta] = None
)

case class Graph(
  id: Option[ID] = None, 
  lbl: Label = None, 
  nodes: List[Node],
  edges: List[Edge],
  equivalentNodesSets : Option[String] = None,
  logicalDefinitionAxioms : Option[String] = None,
  domainRangeAxioms : Option[String] = None,
  propertyChainAxioms : Option[String] = None
)
case class GraphDocument (
  meta : Option[Meta] = None,
  graphs : List[Graph],
  context : Option[String] = None
)

object OboGraphJsonProtocol extends DefaultJsonProtocol  {
  implicit val xrefFormat: JsonFormat[Xref] = lazyFormat(jsonFormat(Xref, "val", "lbl", "meta"))
  implicit val bpvFormat: JsonFormat[BasicPropertyValue] = lazyFormat(jsonFormat(BasicPropertyValue, "pred", "val", "meta", "xrefs"))
  implicit val spvFormat: JsonFormat[SynonymPropertyValue] = lazyFormat(jsonFormat(SynonymPropertyValue, "pred", "val", "meta", "xrefs"))
  implicit val metaFormat = jsonFormat8(Meta)
  implicit val nodeFormat = jsonFormat3(Node)
}

object FromOwl {

  def toGraphDocument(ontology: OWLOntology) = {
    val gd = GraphDocument(graphs=List())
    gd
  }
  def toGraph(ontology: OWLOntology) = {
    val g = Graph(nodes=List(), edges=List())
    for {
      SubClassOf(_, subclass, ObjectSomeValuesFrom(property, filler)) <- ontology.getAxioms
    } yield {
      println(s"$property $filler")
    }
    g
  }
  
}
