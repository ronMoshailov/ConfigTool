import sys
from PyQt6.QtWidgets import (
    QApplication, QWidget, QVBoxLayout, QPushButton,
    QGraphicsView, QGraphicsScene, QGraphicsItem, QInputDialog
)
from PyQt6.QtGui import QPainter, QPen
from PyQt6.QtCore import QRectF, Qt, QPointF, QLineF


# =======================
# MODEL
# =======================

class NodeModel:
    def __init__(self, node_id, node_type):
        self.id = node_id
        self.type = node_type
        self.outputs = []  # list of (condition, target_id)


# =======================
# GRAPHICS ITEMS
# =======================

class NodeItem(QGraphicsItem):
    def __init__(self, model):
        super().__init__()
        self.model = model
        self.edges = []  # רשימת EdgeItems מחוברים
        self.setFlags(
            QGraphicsItem.GraphicsItemFlag.ItemIsMovable |
            QGraphicsItem.GraphicsItemFlag.ItemIsSelectable
        )

    def itemChange(self, change, value):
        if change == QGraphicsItem.GraphicsItemChange.ItemPositionChange:
            for edge in self.edges:
                edge.update_position()
        return super().itemChange(change, value)

    def boundingRect(self):
        return QRectF(-40, -25, 80, 50)

    def paint(self, painter, option, widget):
        painter.setPen(QPen(Qt.GlobalColor.black, 2))
        rect = self.boundingRect()

        if self.model.type == "decision":
            painter.drawPolygon([
                rect.center() + QPointF(0, -25),
                rect.center() + QPointF(40, 0),
                rect.center() + QPointF(0, 25),
                rect.center() + QPointF(-40, 0),
            ])
        else:
            painter.drawEllipse(rect)

        painter.drawText(rect, Qt.AlignmentFlag.AlignCenter, self.model.id)


class EdgeItem(QGraphicsItem):
    def __init__(self, src, dst, condition=""):
        super().__init__()
        self.src = src
        self.dst = dst
        self.condition = condition
        self.setZValue(-1)
        self.src.edges.append(self)
        self.dst.edges.append(self)
        self.line = QLineF(self.src.pos(), self.dst.pos())

    def update_position(self):
        self.prepareGeometryChange()
        self.line.setP1(self.src.pos())
        self.line.setP2(self.dst.pos())
        self.update()

    def boundingRect(self):
        # להוסיף קצת מרווח בשביל הטקסט
        extra = 20
        return QRectF(self.line.p1(), self.line.p2()).normalized().adjusted(-extra, -extra, extra, extra)

    def paint(self, painter, option, widget):
        painter.setPen(QPen(Qt.GlobalColor.black, 2))
        painter.drawLine(self.line)
        if self.condition:
            mid = (self.line.p1() + self.line.p2()) / 2
            painter.drawText(mid, self.condition)


# =======================
# VIEW
# =======================

class GraphView(QGraphicsView):
    def __init__(self, scene, editor):
        super().__init__(scene)
        self.editor = editor

    def mousePressEvent(self, event):
        item = self.scene().itemAt(self.mapToScene(event.pos()), self.transform())
        if isinstance(item, NodeItem):
            self.editor.node_clicked(item)
        super().mousePressEvent(event)

    def wheelEvent(self, event):
        factor = 1.25 if event.angleDelta().y() > 0 else 0.8
        self.scale(factor, factor)


# =======================
# EDITOR
# =======================

class GraphEditor(QWidget):
    def __init__(self):
        super().__init__()
        self.setWindowTitle("Mini Flow Editor")
        self.resize(800, 600)

        self.scene = QGraphicsScene()
        self.view = GraphView(self.scene, self)

        self.graph = {}
        self.selected_node = None
        self.node_count = 0

        btn_dec = QPushButton("Add Decision")
        btn_phase = QPushButton("Add Phase")

        btn_dec.clicked.connect(lambda: self.add_node("decision"))
        btn_phase.clicked.connect(lambda: self.add_node("phase"))

        layout = QVBoxLayout(self)
        layout.addWidget(btn_dec)
        layout.addWidget(btn_phase)
        layout.addWidget(self.view)

    def add_node(self, node_type):
        self.node_count += 1
        model = NodeModel(f"N{self.node_count}", node_type)
        item = NodeItem(model)
        item.setPos(0, 0)
        self.scene.addItem(item)
        self.graph[item] = model

    def node_clicked(self, item):
        if self.selected_node and self.selected_node != item:
            # בקשת תנאי לחיבור
            condition, ok = QInputDialog.getText(self, "Edge Condition", "Enter condition:")
            if not ok:
                condition = ""
            edge = EdgeItem(self.selected_node, item, condition)
            self.scene.addItem(edge)
            self.graph[self.selected_node].outputs.append(
                (condition, self.graph[item].id)
            )
            self.selected_node = None
        else:
            self.selected_node = item


# =======================
# RUN
# =======================

if __name__ == "__main__":
    app = QApplication(sys.argv)
    editor = GraphEditor()
    editor.show()
    sys.exit(app.exec())
