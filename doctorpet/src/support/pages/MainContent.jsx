import React from "react";
import "../css/home.css"

const MainContent = ({ appointments, formatDateTime, getLocation, getBadgeClass }) => {
    return (
        <div className="main-content">
            <div className="content-wrapper">
                <h2 className="page-title">Danh sách lịch hẹn</h2>

                <div>
                    {appointments.map((app, i) => (
                        <div key={i} className="appointment-card">
                            <div className="card-header">
                                <div className="pet-info">
                                    <div className="pet-avatar">{app.pet.name[0]}</div>
                                    <div>
                                        <h3 className="pet-name">{app.pet.name}</h3>
                                        <p className="pet-vet">với {app.vet.name}</p>
                                    </div>
                                </div>
                                <div className={`appointmentType ${getBadgeClass(app.appointmentType)} `}>
                                    {app.appointmentType === 'EMERGENCY' ? 'KHẨN CẤP' : 'Bình Thường'}
                                </div>

                            </div>

                            <div className="info-grid">
                                <div className="info-item">
                                    <i className="ri-calendar-line icon" style={{ color: '#2563eb' }}></i>
                                    <span><strong>Thời gian:</strong> {formatDateTime(app.timeStart)}</span>
                                </div>

                                <div className="info-item">
                                    <i className={`icon ${app.locationType === 'AT_HOME'
                                        ? 'ri-home-4-line'
                                        : app.locationType === 'AT_CLINIC'
                                            ? 'ri-hospital-line'
                                            : 'ri-video-chat-line'
                                        }`}
                                    >
                                    </i>
                                    <span><strong>Hình thức:</strong> {getLocation(app.locationType)}</span>
                                </div>
                                <div className={`type `}>
                                    <i className="ri-stethoscope-line" style={{ color: "#0ea5e9" }}></i>

                                    <strong> Loại khám: </strong> {app.type}
                                </div>

                            </div>

                            <div className="info-grid">
                                <div className="info-item">
                                    <i className="ri-file-text-line icon" style={{ color: '#6b7280' }}></i>
                                    <span><strong>Ghi chú:</strong> {app.notes}</span>
                                </div>
                            </div>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
};

export default MainContent;
