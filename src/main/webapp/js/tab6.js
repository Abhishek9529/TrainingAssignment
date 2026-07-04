// ===============================
// TAB 6 - Prefix Master (REST API)
// ===============================

document.addEventListener("DOMContentLoaded", function () {

    loadPrefixes();

    // Save button
    document.getElementById("saveBtn").addEventListener("click", savePrefix);

});

// Load all Prefix records
function loadPrefixes() {

    fetch(CTX + "/api/prefix")

        .then(response => response.json())

        .then(data => {

            let tbody = document.getElementById("prefixTableBody");

            tbody.innerHTML = "";

            data.forEach(function (prefix) {

                let row = `
                    <tr>
                        <td>${prefix.id}</td>
                        <td>${prefix.prefix}</td>
                        <td>${prefix.gender}</td>
                        <td>${prefix.prefixOf}</td>
                        <td>
                            <button onclick="deletePrefix(${prefix.id})"
                                    style="padding:4px 12px; background:#dc3545; color:white; border:none; cursor:pointer; border-radius:3px;">
                                Delete
                            </button>
                        </td>
                    </tr>
                `;

                tbody.innerHTML += row;

            });

        })

        .catch(error => {

            console.error(error);

        });

}

// Save a new Prefix record
function savePrefix() {

    let prefixVal = document.getElementById("prefix").value.trim();
    let genderVal = document.getElementById("gender").value;
    let prefixOfVal = document.getElementById("prefixOf").value.trim();

    // Validate all three fields
    if (!prefixVal) {
        alert("Prefix field is required.");
        return;
    }
    if (!genderVal) {
        alert("Gender field is required.");
        return;
    }
    if (!prefixOfVal) {
        alert("Prefix Of field is required.");
        return;
    }

    let payload = {
        prefix: prefixVal,
        gender: genderVal,
        prefixOf: prefixOfVal
    };

    console.log("Sending payload:", JSON.stringify(payload));

    fetch(CTX + "/api/prefix", {

        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)

    })

    .then(response => {
        console.log("Response status:", response.status);
        return response.text().then(text => {
            console.log("Response body:", text);
            if (response.ok) {
                // Clear all input fields
                document.getElementById("prefix").value = "";
                // Reset Gender dropdown to default
                document.getElementById("gender").selectedIndex = 0;
                document.getElementById("prefixOf").value = "";
                // Reload table without page refresh
                loadPrefixes();
                alert("Record saved successfully!");
            } else {
                alert("Error saving record: " + text);
            }
        });

    })

    .catch(error => {
        console.error(error);
        alert("Error saving record: " + error.message);
    });

}

// Delete a Prefix record by ID
function deletePrefix(id) {

    if (!confirm("Delete this record?")) {
        return;
    }

    fetch(CTX + "/api/prefix/" + id, {

        method: "DELETE"

    })

    .then(response => {

        if (response.ok) {
            loadPrefixes();
        } else {
            alert("Error deleting record.");
        }

    })

    .catch(error => {
        console.error(error);
        alert("Error deleting record.");
    });

}