package org.jellyfin.mobilec.setup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import org.jellyfin.mobilec.MainViewModel
import org.jellyfin.mobilec.databinding.FragmentComposeBinding
import org.jellyfin.mobilec.ui.screens.connect.ConnectScreen
import org.jellyfin.mobilec.ui.utils.AppTheme
import org.jellyfin.mobilec.utils.Constants
import org.jellyfin.mobilec.utils.applyWindowInsetsAsMargins
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class ConnectFragment : Fragment() {
    private val mainViewModel: MainViewModel by activityViewModel()
    private var _viewBinding: FragmentComposeBinding? = null
    private val viewBinding get() = _viewBinding!!
    private val composeView: ComposeView get() = viewBinding.composeView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _viewBinding = FragmentComposeBinding.inflate(inflater, container, false)
        return composeView.apply { applyWindowInsetsAsMargins() }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Apply window insets
        ViewCompat.requestApplyInsets(composeView)

        val encounteredConnectionError = arguments?.getBoolean(Constants.FRAGMENT_CONNECT_EXTRA_ERROR) == true

        composeView.setContent {
            AppTheme {
                ConnectScreen(
                    mainViewModel = mainViewModel,
                    showExternalConnectionError = encounteredConnectionError,
                )
            }
        }
    }
}
