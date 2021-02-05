package com.weique.overhaul.v2.mvp.model.entity;

import java.util.List;

public class PointsListBean {

        private List<ElementPointsBean> elementPoints;
        private List<ElementTypesBean> elementTypes;

        public List<ElementPointsBean> getElementPoints() {
            return elementPoints;
        }

        public void setElementPoints(List<ElementPointsBean> elementPoints) {
            this.elementPoints = elementPoints;
        }

        public List<ElementTypesBean> getElementTypes() {
            return elementTypes;
        }

        public void setElementTypes(List<ElementTypesBean> elementTypes) {
            this.elementTypes = elementTypes;
        }

        public static class ElementPointsBean {
            /**
             * Id : 1aa170d2-f84f-4bc6-8271-0ed8df9bdd90
             * point : {"lng":116.947762,"lat":34.728399}
             * distance : 51.9336
             * elementTypeName : 网格员
             * elementTypeId : 5332f9fc-c1eb-49f9-aa64-04d045b165f1
             */

            private String CustId;



            public String getCustId() {
                return CustId;
            }

            public void setCustId(String custId) {
                CustId = custId;
            }

            private PointsBean.PointBean point;
            private double distance;
            private String elementTypeName;
            private String elementTypeId;


            public PointsBean.PointBean getPoint() {
                return point;
            }

            public void setPoint(PointsBean.PointBean point) {
                this.point = point;
            }

            public double getDistance() {
                return distance;
            }

            public void setDistance(double distance) {
                this.distance = distance;
            }

            public String getElementTypeName() {
                return elementTypeName;
            }

            public void setElementTypeName(String elementTypeName) {
                this.elementTypeName = elementTypeName;
            }

            public String getElementTypeId() {
                return elementTypeId;
            }

            public void setElementTypeId(String elementTypeId) {
                this.elementTypeId = elementTypeId;
            }

        }

        public static class ElementTypesBean {
            /**
             * Id : 1267dbb8-b8fd-42ea-8a90-b344d57ebe71
             * Name : 人员基础信息表
             */

            private String Id;
            private String Name;

            public String getId() {
                return Id;
            }

            public void setId(String Id) {
                this.Id = Id;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }
        }
}
