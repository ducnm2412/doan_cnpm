// src/pages/ProfileSupport.js

export const appointments = [
    {
        timeStart: "2025-01-25T14:00:00+07:00",
        type: "Cấp cứu",
        appointmentType: "EMERGENCY",
        locationType: "AT_HOME",
        pet: { id: 2, name: "Mít" },
        vet: { id: 3, name: "BS. Nguyễn Văn A" },
        notes: "Thú cưng bị nôn mửa và không ăn được "
    },
    {
        timeStart: "2025-02-01T09:30:00+07:00",
        type: "Khám định kỳ",
        appointmentType: "NORMAL",
        locationType: "AT_CLINIC",
        pet: { id: 1, name: "Bông" },
        vet: { id: 4, name: "BS. Trần Thị B" },
        notes: "Khám sức khỏe tổng quát"
    },
    {
        timeStart: "2025-02-05T16:00:00+07:00",
        type: "Tiêm phòng",
        appointmentType: "NORMAL",
        locationType: "AT_CLINIC",
        pet: { id: 5, name: "Tẹo" },
        vet: { id: 2, name: "BS. Lê Văn C" },
        notes: "Tiêm phòng dại mũi thứ 2"
    },
    {
        timeStart: "2025-02-10T10:15:00+07:00",
        type: "Tư vấn dinh dưỡng",
        appointmentType: "NORMAL",
        locationType: "ONLINE",
        pet: { id: 3, name: "Mun" },
        vet: { id: 1, name: "BS. Phạm Thị D" },
        notes: "Tư vấn chế độ ăn uống do thú cưng thừa cân"
    },
    {
        timeStart: "2025-02-12T19:45:00+07:00",
        type: "Phẫu thuật",
        appointmentType: "EMERGENCY",
        locationType: "AT_CLINIC",
        pet: { id: 4, name: "Cá" },
        vet: { id: 3, name: "BS. Nguyễn Văn A" },
        notes: "Cắt móng và xử lý vết thương nhẹ "
    }
];

export const formatDateTime = (dateString) => {
    const d = new Date(dateString);
    const pad = (n) => n.toString().padStart(2, '0');
    return `${pad(d.getDate())}/${pad(d.getMonth() + 1)}/${d.getFullYear()} - ${pad(d.getHours())}:${pad(d.getMinutes())}`;
};

export const getLocation = (loc) => {
    return loc === "AT_HOME" ? "Tại nhà" :
        loc === "AT_CLINIC" ? "Tại phòng khám" :
            loc === "ONLINE" ? "Tư vấn online" : loc;
};

export const getBadgeClass = (type) => {
    const map = {
        EMERGENCY: "badge-emergency",
        NORMAL: "badge-normal"
    };
    return map[type] || "badge";
};
